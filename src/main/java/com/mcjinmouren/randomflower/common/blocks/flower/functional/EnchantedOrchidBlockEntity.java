package com.mcjinmouren.randomflower.common.blocks.flower.functional;

import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.item.BotaniaItems;

public class EnchantedOrchidBlockEntity extends FunctionalFlowerBlockEntity {

    private static final int RANGE = 4;

    private static final String TAG_MANA = "consumed";
    private static final String TAG_COST = "cost";
    private static final String TAG_INGOT = "hasingot";

    int consumed = 0;
    int shouldCost = 0;
    boolean hasIngot = false;

    public EnchantedOrchidBlockEntity(BlockPos pos, BlockState state) {
        super(RandomFlowerFlowerBlocks.ENCHANTEDORCHID, pos, state);
    }

    public void tickFlower() {
        super.tickFlower();

        if(!hasIngot && this.redstoneSignal <= 0){
            for(ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE),
                    getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1)))){
                if(item.getItem().getItem() == BotaniaItems.blackLotus && item.getItem().getCount() > 0){
                    item.getItem().shrink(1);
                    hasIngot = true;
                    break;
                }
            }
        }


        if (!this.getLevel().isClientSide && this.redstoneSignal <= 0  && hasIngot){
            if (this.ticksExisted % 1200 == 0 && this.getMana() >= 250000) {
                BlockPos pos = new BlockPos(this.getEffectivePos().getX() - 4 + this.getLevel().random.nextInt(9), this.getEffectivePos().getY() - 1
                        , this.getEffectivePos().getZ() - 4 + this.getLevel().random.nextInt(9));
                BlockPos up = pos.above();
                BlockState flower = BotaniaBlocks.getFlower(DyeColor.byId(1)).defaultBlockState();
                if (flower.canSurvive(this.getLevel(), up)){
                    this.getLevel().setBlockAndUpdate(pos, BotaniaBlocks.enchantedSoil.defaultBlockState());
                    hasIngot = false;
                    addMana(-250000);
                }
            }
        }
    }

    @Override
    public int getColor() {
        return 0x4B0082;
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_MANA, consumed);
        cmp.putInt(TAG_COST, shouldCost);
        cmp.putBoolean(TAG_INGOT, hasIngot);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        consumed = cmp.getInt(TAG_MANA);
        shouldCost = cmp.getInt(TAG_COST);
        hasIngot = cmp.getBoolean(TAG_INGOT);
    }

    @Override
    public int getMaxMana() {
        return 250000;
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }

}
