package com.mcjinmouren.randomflower.common.blocks.flower.generating;

import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

import java.util.Objects;

/**
 * Bell Flower
 * 风铃草
 */

public class BellFlowerBlockEntity extends GeneratingFlowerBlockEntity {
    public static final String TAG_PASSIVE_DECAY_TICKS = "passiveDecayTicks";
    private static final int RANGE = 2;
    public static final int DECAY_TIME = 54000;
    private int passiveDecayTicks;

    public BellFlowerBlockEntity(BlockPos pos, BlockState state) {
        super(RandomFlowerFlowerBlocks.BELLFLOWER, pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        int baseGen = 10;
        int baseY = 90;
        int y = this.getEffectivePos().getY();

        /*
          get pos and add mana.
          获取坐标并增加魔力。
         */
        if(Objects.requireNonNull(this.getLevel()).canSeeSky(this.getEffectivePos()) && y > baseY){
            int rain = this.getLevel().isRaining() ? 3 : 0;
            int gen = (baseGen + rain) * y / baseY;
            if(this.ticksExisted % 10 == 0)
                addMana(gen);
        }
        /*
          If the lifespan is reached, turn the flower into a withered bush.
          如果到达了寿命，把花变成枯死的灌木。
         */
        if (!this.getLevel().isClientSide && ++this.passiveDecayTicks > DECAY_TIME) {
            this.getLevel().destroyBlock(this.getBlockPos(), false);
            if (Blocks.DEAD_BUSH.defaultBlockState().canSurvive(this.getLevel(), this.getBlockPos())) {
                this.getLevel().setBlockAndUpdate(this.getBlockPos(), Blocks.DEAD_BUSH.defaultBlockState());
            }
        }
    }

    @Override
    public int getMaxMana() {
        return 300;
    }

    @Override
    public int getColor() {
        return 0xFFFF99;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_PASSIVE_DECAY_TICKS, this.passiveDecayTicks);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        this.passiveDecayTicks = cmp.getInt(TAG_PASSIVE_DECAY_TICKS);
    }

}
