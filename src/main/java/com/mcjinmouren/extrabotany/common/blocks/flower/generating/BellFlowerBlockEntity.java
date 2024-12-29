package com.mcjinmouren.extrabotany.common.blocks.flower.generating;

import com.mcjinmouren.extrabotany.common.blocks.ExtraBotanyFlowerBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

import java.util.Objects;

public class BellFlowerBlockEntity extends GeneratingFlowerBlockEntity {
    public static final String TAG_PASSIVE_DECAY_TICKS = "passiveDecayTicks";
    private static final int RANGE = 2;
    public static final int DECAY_TIME = 54000;
    private int passiveDecayTicks;

    public BellFlowerBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyFlowerBlocks.BELLFLOWER, pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        int baseGen = 10;
        int baseY = 90;
        int y = this.getEffectivePos().getY();

        if(Objects.requireNonNull(this.getLevel()).canSeeSky(this.getEffectivePos()) && y > baseY){
            int rain = this.getLevel().isRaining() ? 3 : 0;
            int gen = (baseGen + rain) * y / baseY;
            if(this.ticksExisted % 10 == 0)
                addMana(gen);
        }
        if (!this.getLevel().isClientSide && ++this.passiveDecayTicks > 54000) {
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
        cmp.putInt("passiveDecayTicks", this.passiveDecayTicks);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        this.passiveDecayTicks = cmp.getInt("passiveDecayTicks");
    }

}
