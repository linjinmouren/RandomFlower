package com.mcjinmouren.randomflower.api;

import com.mcjinmouren.randomflower.RandomFlower;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

/**
 * This class provides the resource IDs for the inventory items of this mod.
 * 该类提供本模组物品栏的资源ID。
 */

public class RandomFlowerRegistries {

    public static final ResourceKey<CreativeModeTab> RANDOMFLOWER_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            new ResourceLocation(RandomFlower.MOD_ID, "randomflower"));

}
