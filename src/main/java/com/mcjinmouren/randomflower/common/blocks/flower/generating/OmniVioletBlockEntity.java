package com.mcjinmouren.randomflower.common.blocks.flower.generating;

import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.common.handler.BotaniaSounds;

public class OmniVioletBlockEntity extends GeneratingFlowerBlockEntity {

    private static final String TAG_BURN_TIME = "burnTime";
    private static final int FUEL_CAP = 32000;
    private static final int RANGE = 2;
    int burnTime = 0;

    public OmniVioletBlockEntity(BlockPos pos, BlockState state) {
        super(RandomFlowerFlowerBlocks.OMNIVIOLET, pos, state);
    }

    private float getPower(Level level, BlockPos pos) {
        return level.getBlockState(pos).getEnchantPowerBonus(level, pos);
    }

    private float getPower() {
        float power = 0F;
        for (int k = -1; k <= 1; ++k) {
            for (int l = -1; l <= 1; ++l) {
                if ((k != 0 || l != 0) && getLevel().isEmptyBlock(getEffectivePos().offset(l, 0, k)) && getLevel().isEmptyBlock(getEffectivePos().offset(l, 1, k))) {
                    power += getPower(getLevel(), getEffectivePos().offset(l * 2, 0, k * 2));
                    power += getPower(getLevel(), getEffectivePos().offset(l * 2, 1, k * 2));

                    if (l != 0 && k != 0) {
                        power += getPower(getLevel(), getEffectivePos().offset(l * 2, 0, k));
                        power += getPower(getLevel(), getEffectivePos().offset(l * 2, 1, k));
                        power += getPower(getLevel(), getEffectivePos().offset(l, 0, k * 2));
                        power += getPower(getLevel(), getEffectivePos().offset(l, 1, k * 2));
                    }
                }
            }
        }
        return power;
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        float buff = 1 + getPower() * 0.05F;

        if (burnTime > 0) {
            burnTime--;
            addMana((int) (8 * Math.min(7F, buff)));
        }

        if (bindingPos != null) {
            if (burnTime == 0) {
                if (getMana() < getMaxMana()) {
                    for (ItemEntity item : getLevel().getEntitiesOfClass(ItemEntity.class,
                            new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE),
                                    getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1)))) {
                        if (item.tickCount >= 59 && !item.isRemoved()) {
                            ItemStack stack = item.getItem();
                            if (stack.isEmpty())
                                continue;

                            int burnTime = stack.getItem() == Items.BOOK ? 50 : stack.getItem() == Items.WRITTEN_BOOK ? 65 : 0;
                            if (burnTime > 0 && stack.getCount() > 0) {
                                this.burnTime = Math.min(FUEL_CAP, burnTime);

                                stack.shrink(1);
                                addMana(8);
                                getLevel().playSound(null, getEffectivePos(), BotaniaSounds.endoflame,
                                        SoundSource.BLOCKS, 0.2F, 1F);
                                sync();

                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getMaxMana() {
        return 1500;
    }

    @Override
    public int getColor() {
        return 0xEE82EE;
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_BURN_TIME, burnTime);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        burnTime = cmp.getInt(TAG_BURN_TIME);
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

}
