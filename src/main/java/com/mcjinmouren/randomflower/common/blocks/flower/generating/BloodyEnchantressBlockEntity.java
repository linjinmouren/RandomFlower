package com.mcjinmouren.randomflower.common.blocks.flower.generating;

import com.mcjinmouren.randomflower.common.base.AdvancementHandler;
import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import com.mcjinmouren.randomflower.common.effect.RandomFlowerEffects;
import com.mcjinmouren.randomflower.common.lib.LibAdvancementNames;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class BloodyEnchantressBlockEntity extends GeneratingFlowerBlockEntity {
    private static final String TAG_BURN_TIME = "burnTime";
    private static final int RANGE = 1;
    private static final int COOLDOWN_EVENT = 0;
    private int burnTime = 0;

    public BloodyEnchantressBlockEntity(BlockPos pos, BlockState state) {
        super(RandomFlowerFlowerBlocks.BLOODYENCHANTRESS, pos, state);
    }

    public void tickFlower() {
        super.tickFlower();
        if (burnTime > 0) {
            burnTime--;
            return;
        }
        if (!isValidBinding() || getMana() >= getMaxMana() || level == null) return;
        Stream<LivingEntity> tmp = level.getEntitiesOfClass(LivingEntity.class, new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE), getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1))).stream().filter(entity->!entity.isRemoved());
        List<LivingEntity> ampArr = tmp.toList();
        if (ampArr.isEmpty())return;
        int[] ampNum= ampArr.stream().map(entity -> entity.hasEffect(RandomFlowerEffects.blood_temptation) ? Objects.requireNonNull(entity.getEffect(RandomFlowerEffects.blood_temptation)).getAmplifier() : 0).mapToInt(Integer::intValue).toArray();
        int ampAll= Arrays.stream(ampNum).sum();
        if (ampAll > 35) return;
        for (int i = 0; i < ampNum.length; i++) {
            if (getMana() < getMaxMana()) {
                if (Math.random() > 0.5F) {
                    int amp = ampNum[i];
                    LivingEntity living=ampArr.get(i);
                    if (amp > 4) continue;
                    addMana(500 - 20 * amp - 10 * ampAll);
                    living.addEffect(new MobEffectInstance(RandomFlowerEffects.blood_temptation, 8 * 20, amp + 1));
                    if (living instanceof ServerPlayer) AdvancementHandler.INSTANCE.grantAdvancement((ServerPlayer) living, LibAdvancementNames.BLOODY_ENCHANTRESS_USE);
                    living.hurt(Objects.requireNonNull(this.getLevel()).damageSources().magic(), 4.0F);
                    burnTime += 20;
                }
            } else break;
        }
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
    public boolean triggerEvent(int event, int param) {
        if (event == COOLDOWN_EVENT) {
            Entity e = null;
            if (level != null) {
                e = level.getEntity(param);
            }
            if (e != null) {
                e.level().addParticle(ParticleTypes.LARGE_SMOKE, e.getX(), e.getY() + 0.1, e.getZ(), 0.0D, 0.0D, 0.0D);
                e.level().addParticle(ParticleTypes.FLAME, e.getX(), e.getY(), e.getZ(), 0.0D, 0.0D, 0.0D);
            }
            return true;
        } else {
            return super.triggerEvent(event, param);
        }
    }
    @Override
    public int getMaxMana() {
        return 800;
    }
    @Override
    public int getColor() {
        return 0x8B0000;
    }
    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
    }
}
