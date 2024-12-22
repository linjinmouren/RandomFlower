package com.mcjinmouren.extrabotany.common.Block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ModTiles {
    private static final Map<ResourceLocation, BlockEntityType<?>> ALL = new HashMap<>();

    public static void registerTiles(BiConsumer<BlockEntityType<?>, ResourceLocation> r)
    {
        for (var e : ALL.entrySet())
        {
            r.accept(e.getValue(), e.getKey());
        }
    }

    public static void registerWandHudCaps(BotaniaBlockEntities.BECapConsumer<WandHUD> consumer)
    {

    }
}
