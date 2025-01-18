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

import java.util.List;
import java.util.Objects;

/**
 * Mirrow Tunia
 * 镜姬
 */

public class MirrowtuniaBlockEntity extends FunctionalFlowerBlockEntity {

    public MirrowtuniaBlockEntity(BlockPos pos, BlockState state) {
        super(RandomFlowerFlowerBlocks.MIRROWTUNIA, pos, state);
    }

    public void tickFlower() {
        super.tickFlower();
        if (level != null && level.isClientSide && this.redstoneSignal <= 0) {
            List<LivingEntity> entities = Objects.requireNonNull(this.getLevel()).getEntitiesOfClass(LivingEntity.class, new AABB(this.getEffectivePos().offset(-6, -6, -6), this.getEffectivePos().offset(7, 7, 7)));

            /*
              Iterate through each entity. If it is a player, apply a potion effect.
              遍历每个实体，如果是玩家，给予药水效果。
             */
            for (LivingEntity entity : entities) {
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
