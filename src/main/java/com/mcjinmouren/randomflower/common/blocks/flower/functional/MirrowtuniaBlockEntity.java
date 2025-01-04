package com.mcjinmouren.randomflower.common.blocks.flower.functional;

import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import com.mcjinmouren.randomflower.common.effect.RandomFlowerEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

import java.util.Iterator;
import java.util.List;

public class MirrowtuniaBlockEntity extends FunctionalFlowerBlockEntity {

    public MirrowtuniaBlockEntity(BlockPos pos, BlockState state) {
        super(RandomFlowerFlowerBlocks.MIRROWTUNIA, pos, state);
    }

    public void tickFlower() {
        super.tickFlower();
        if (!this.getLevel().isClientSide && this.redstoneSignal <= 0) {
            List<LivingEntity> entities = this.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(this.getEffectivePos().offset(-6, -6, -6), this.getEffectivePos().offset(7, 7, 7)));
            Iterator var2 = entities.iterator();

            while(var2.hasNext()) {
                LivingEntity entity = (LivingEntity)var2.next();
                if ((entity instanceof Player) && entity.getEffect(RandomFlowerEffects.reflect) == null && this.getMana() >= 20 && !entity.level().isClientSide && entity.getMobType() != MobType.UNDEAD) {
                    entity.addEffect(new MobEffectInstance(RandomFlowerEffects.reflect, 8 * 30, 0));
                    this.addMana(-20);
                }
            }

        }
    }

    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public int getMaxMana() {
        return 2000;
    }

    @Override
    public int getColor() {
        return 0X4169E1;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(this.getEffectivePos(), 6);
    }
}
