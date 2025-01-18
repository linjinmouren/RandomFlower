package com.mcjinmouren.randomflower.forge;

import com.mcjinmouren.randomflower.RandomFlower;
import com.mcjinmouren.randomflower.api.RandomFlowerRegistries;
import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import com.mcjinmouren.randomflower.common.blocks.flower.functional.ManalinkuimBlockEntity;
import com.mcjinmouren.randomflower.common.effect.MobEffects.Reflect;
import com.mcjinmouren.randomflower.common.effect.RandomFlowerEffects;
import com.mcjinmouren.randomflower.common.items.RandomFlowerItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import vazkii.botania.common.item.CustomCreativeTabContents;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * This class is used for the registration of all content.
 * 这个类用于对所有内容注册。
 */

@Mod(RandomFlower.MOD_ID)
public class ForgeCommonInitializer {

    public ForgeCommonInitializer() {
        //Get and register 获取并注册
        registryInit();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    public void commonSetup(FMLCommonSetupEvent evt) {
        //注册事件
        registerEvents();

        //add FlowerPots 添加盆栽里的花
        evt.enqueueWork(()->{
            BiConsumer<ResourceLocation, Supplier<? extends Block>> consumer = (resourceLocation, blockSupplier) -> ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(resourceLocation, blockSupplier);
            RandomFlowerFlowerBlocks.registerFlowerPotPlants(consumer);
        });
    }

    private void registryInit(){
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        bind(Registries.BLOCK, RandomFlowerFlowerBlocks::registerBlocks);
        bindForItems(RandomFlowerFlowerBlocks::registerItemBlocks);
        bind(Registries.BLOCK_ENTITY_TYPE, RandomFlowerFlowerBlocks::registerTEs);

        bind(Registries.MOB_EFFECT, RandomFlowerEffects::registerPotions);

        bindForItems(RandomFlowerItems::registerItems);

        /*
          This is the implementation of the mod's inventory, which will automatically add items to it.
          以下是这个Mod的物品栏实现，会自动添加物品至其中。
         */
        bind(Registries.CREATIVE_MODE_TAB, consumer -> {
            consumer.accept(CreativeModeTab.builder()
                            .title(Component.translatable("randomflower.creativetab.mod_tab"))
                            .icon(() -> new ItemStack(RandomFlowerFlowerBlocks.manalinkuim))
                            .withSearchBar()
                            .build(),
                    RandomFlowerRegistries.RANDOMFLOWER_TAB_KEY.location());
        });
        modBus.addListener((BuildCreativeModeTabContentsEvent e) -> {
            if (e.getTabKey() == RandomFlowerRegistries.RANDOMFLOWER_TAB_KEY) {
                for (Item item : this.itemsToAddToCreativeTab) {
                    /*
                      If the item specifies a registered item in the inventory, then add the specified item.
                      如果物品指定了在物品栏中的注册物品，则添加所指定的物品。
                     */
                    if (item instanceof CustomCreativeTabContents cc) {
                    /*
                      Implemented through the CustomCreativeTabContents interface.
                      通过CustomCreativeTabContents接口实现。
                     */
                        cc.addToCreativeTab(item, e);
                    } else if (item instanceof BlockItem bi && bi.getBlock() instanceof CustomCreativeTabContents cc) {
                        cc.addToCreativeTab(item, e);
                    } else {
                        e.accept(item);
                    }
                }
            }
        });
    }

    private void registerEvents(){
        IEventBus bus = MinecraftForge.EVENT_BUS;
        //Register Mod's events
        //注册模组的事件
        bus.addListener(Reflect::onLivingAttacked);
        bus.addListener(ManalinkuimBlockEntity::onPlayerUseItem);
    }

    private static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

    /**
     * For items registered using the bindForItems method, each will be added to itemsToAddToCreativeTab.
     * When registering the creative tab, they will be automatically iterated over and added.
     * 对于使用bindForItems方法注册的物品，每个都会被添加至itemsToAddToCreativeTab，当注册物品栏时，会自动遍历并添加。
     */
    private final Set<Item> itemsToAddToCreativeTab = new LinkedHashSet<>();

    private void bindForItems(Consumer<BiConsumer<Item, ResourceLocation>> source) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((RegisterEvent event) -> {
            if (event.getRegistryKey().equals(Registries.ITEM)) {
                source.accept((t, rl) -> {
                    itemsToAddToCreativeTab.add(t);
                    event.register(Registries.ITEM, rl, () -> t);
                });
            }
        });
    }
}
