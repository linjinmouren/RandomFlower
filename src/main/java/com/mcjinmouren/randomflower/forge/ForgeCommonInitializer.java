package com.mcjinmouren.randomflower.forge;

import com.mcjinmouren.randomflower.RandomFlower;
import com.mcjinmouren.randomflower.common.RandomFlowerCreativeModeTab;
import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import com.mcjinmouren.randomflower.common.blocks.flower.functional.ManalinkuimBlockEntity;
import com.mcjinmouren.randomflower.common.effect.RandomFlowerEffects;
import com.mcjinmouren.randomflower.common.effect.MobEffects.Reflect;
import com.mcjinmouren.randomflower.common.items.RandomFlowerItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod(RandomFlower.MOD_ID)
public class ForgeCommonInitializer {

    public ForgeCommonInitializer() {
        registryInit();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RandomFlowerItems.register(modEventBus);
        RandomFlowerCreativeModeTab.register(modEventBus);
    }

    public void commonSetup(FMLCommonSetupEvent evt) {
        registerEvents();

        evt.enqueueWork(()->{
            BiConsumer<ResourceLocation, Supplier<? extends Block>> consumer = (resourceLocation, blockSupplier) -> ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(resourceLocation, blockSupplier);
            RandomFlowerFlowerBlocks.registerFlowerPotPlants(consumer);
        });
    }

    private void registryInit(){
        bind(Registries.BLOCK, RandomFlowerFlowerBlocks::registerBlocks);
        bindForItems(RandomFlowerFlowerBlocks::registerItemBlocks);
        bind(Registries.BLOCK_ENTITY_TYPE, RandomFlowerFlowerBlocks::registerTEs);

        bind(Registries.MOB_EFFECT, RandomFlowerEffects::registerPotions);
    }

    private void registerEvents(){
        IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(this::onLivingAttacked);
        bus.addListener(ManalinkuimBlockEntity::onPlayerUseItem);
    }

    public void onLivingAttacked(LivingAttackEvent event) {
        LivingEntity living = event.getEntity();
        Reflect.onLivingAttacked(event);
    }

    private static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

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
