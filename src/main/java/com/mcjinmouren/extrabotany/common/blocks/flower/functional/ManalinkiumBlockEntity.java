package com.mcjinmouren.extrabotany.common.blocks.flower.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class ManalinkiumBlockEntity extends FunctionalFlowerBlockEntity {
    /*
    @
    @     Perhaps it will be realized someday.
    @
     */
    public ManalinkiumBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public int getMaxMana() {
        return 0;
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public @Nullable RadiusDescriptor getRadius() {
        return null;
    }
}
