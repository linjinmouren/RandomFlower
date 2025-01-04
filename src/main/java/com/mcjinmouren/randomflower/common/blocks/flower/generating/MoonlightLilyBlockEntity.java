package com.mcjinmouren.randomflower.common.blocks.flower.generating;

import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

public class MoonlightLilyBlockEntity extends GeneratingFlowerBlockEntity {
    public static final String TAG_PASSIVE_DECAY_TICKS = "passiveDecayTicks";
    public static final int DECAY_TIME = 36000;
    private int passiveDecayTicks;
    int cd = 0;

    public MoonlightLilyBlockEntity(BlockPos pos, BlockState state) {
        super(RandomFlowerFlowerBlocks.MOONLIGHTLILY, pos, state);
    }

    public void tickFlower(){
        super.tickFlower();
        if (cd > 0) cd--;
        if (level != null && level.canSeeSky(getEffectivePos()) && level.isNight() && !level.isClientSide && cd == 0) {
            if (!level.isRaining()){
                addMana(20);
            }else {
                addMana(5);
            }
            cd = 100;
        }
        if (!this.getLevel().isClientSide && ++this.passiveDecayTicks > DECAY_TIME) {
            this.getLevel().destroyBlock(this.getBlockPos(), false);
            if (Blocks.DEAD_BUSH.defaultBlockState().canSurvive(this.getLevel(), this.getBlockPos())) {
                this.getLevel().setBlockAndUpdate(this.getBlockPos(), Blocks.DEAD_BUSH.defaultBlockState());
            }
        }
    }

    @Override
    public int getMaxMana() {
        return 1000;
    }

    @Override
    public int getColor() {
        return 0xFFFF00;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), 6);
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
