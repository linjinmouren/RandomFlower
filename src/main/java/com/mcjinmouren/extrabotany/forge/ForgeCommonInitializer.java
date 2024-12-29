package com.mcjinmouren.extrabotany.forge;

import com.mcjinmouren.extrabotany.ExtraBotany;
import com.mcjinmouren.extrabotany.common.ExtraBotanyCreativeModeTab;
import com.mcjinmouren.extrabotany.common.blocks.ExtraBotanyFlowerBlocks;
import com.mcjinmouren.extrabotany.common.effect.ExtraBotanyEffects;
import com.mcjinmouren.extrabotany.common.effect.MobEffects.Reflect;
import com.mcjinmouren.extrabotany.common.items.ExtraBotanyItems;
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

@Mod(ExtraBotany.MOD_ID)
public class ForgeCommonInitializer {

    public ForgeCommonInitializer() {
        registryInit();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ExtraBotanyItems.register(modEventBus);
        ExtraBotanyCreativeModeTab.register(modEventBus);
    }

    public void commonSetup(FMLCommonSetupEvent evt) {
        registerEvents();

        evt.enqueueWork(()->{
            BiConsumer<ResourceLocation, Supplier<? extends Block>> consumer = (resourceLocation, blockSupplier) -> ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(resourceLocation, blockSupplier);
            ExtraBotanyFlowerBlocks.registerFlowerPotPlants(consumer);
        });
    }

    private void registryInit(){
        bind(Registries.BLOCK, ExtraBotanyFlowerBlocks::registerBlocks);
        bindForItems(ExtraBotanyFlowerBlocks::registerItemBlocks);
        bind(Registries.BLOCK_ENTITY_TYPE, ExtraBotanyFlowerBlocks::registerTEs);

        bind(Registries.MOB_EFFECT, ExtraBotanyEffects::registerPotions);
    }

    private void registerEvents(){
        IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(this::onLivingAttacked);
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
