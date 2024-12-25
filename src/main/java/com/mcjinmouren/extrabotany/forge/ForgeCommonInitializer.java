package com.mcjinmouren.extrabotany.forge;

import com.mcjinmouren.extrabotany.ExtraBotany;
import com.mcjinmouren.extrabotany.common.blocks.ExtraBotanyFlowerBlocks;
import com.mcjinmouren.extrabotany.common.effect.ExtraBotanyEffects;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mod(ExtraBotany.MOD_ID)
public class ForgeCommonInitializer {

    public ForgeCommonInitializer() {
        registryInit();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ExtraBotanyEffects.register(modEventBus);
    }

    public void commonSetup(FMLCommonSetupEvent evt) {

    }

    private void registryInit(){
        bind(Registries.BLOCK, ExtraBotanyFlowerBlocks::registerBlocks);
        bindForItems(ExtraBotanyFlowerBlocks::registerItemBlocks);
        bind(Registries.BLOCK_ENTITY_TYPE, ExtraBotanyFlowerBlocks::registerTEs);
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