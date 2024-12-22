package com.mcjinmouren.extrabotany.common.Block.BlockEntity.GeneratingFlower;

import com.mcjinmouren.extrabotany.Utils;
import com.mcjinmouren.extrabotany.common.Block.ModBlocks;
import com.mcjinmouren.extrabotany.common.Effects.ModEffects;
import com.mcjinmouren.extrabotany.common.base.AdvancementHandler;
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

import java.util.Objects;

public class BloodyEnchantressBlockEntity extends GeneratingFlowerBlockEntity {

    private static final String TAG_BURN_TIME = "burnTime";
    private static final int RANGE = 1;

    private static final int COOLDOWN_EVENT = 0;

    private int burnTime =0;

    public BloodyEnchantressBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.BLOODYENCHANTRESS, pos, state);
    }


    @Override
    public void tickFlower()
    {
        super.tickFlower();
        if (burnTime > 0)
            burnTime--;

        int ampAll = 0;
        if (level != null) {
            for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE), getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1))))
            {
                if (!living.isRemoved())
                {
                    int amp = living.hasEffect(ModEffects.BLOOD_TEMPTATION.get()) ? Objects.requireNonNull(living.getEffect(ModEffects.BLOOD_TEMPTATION.get())).getAmplifier() : 0;
                    ampAll += amp;
                }
            }
        }
        if (ampAll > 35)
            return;

        if (isValidBinding())
        {
            if (burnTime == 0 && getMana() < getMaxMana())
            {
                for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, new AABB(getEffectivePos().offset(-RANGE, -RANGE, -RANGE), getEffectivePos().offset(RANGE + 1, RANGE + 1, RANGE + 1))))
                {
                    if (!living.isRemoved())
                    {
                        int amp = living.hasEffect(ModEffects.BLOOD_TEMPTATION.get()) ? Objects.requireNonNull(living.getEffect(ModEffects.BLOOD_TEMPTATION.get())).getAmplifier() : 0;
                        if (amp > 4 && Math.random() > 0.5F)
                            continue;
                        if (amp < 10)
                        {
                            addMana((int) (25F * 20F * (1F - 0.04F * amp - 0.02F * ampAll)));
                        } else
                            break;
                        int lv = 0;
                        if (living.hasEffect(ModEffects.BLOOD_TEMPTATION.get()))
                        {
                            lv = Objects.requireNonNull(living.getEffect(ModEffects.BLOOD_TEMPTATION.get())).getAmplifier() + 1;
                        }
                        living.addEffect(new MobEffectInstance(ModEffects.BLOOD_TEMPTATION.get(), 8 * 20, lv));
                        if (living instanceof ServerPlayer)
                        {
                            AdvancementHandler.INSTANCE.grantAdvancement((ServerPlayer) living, Utils.BLOODY_ENCHANTRESS_USE);
                        }
                        //living.hurt(Objects.requireNonNull(this.getLevel()).damageSources().magic().bypassArmor().bypassMagic().bypassInvul(), 4F);
                        living.hurt(Objects.requireNonNull(this.getLevel()).damageSources().magic(), 4.0F);
                        burnTime += 20;
                    }
                }
            }
        }
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp)
    {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_BURN_TIME, burnTime);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp)
    {
        super.readFromPacketNBT(cmp);
        burnTime = cmp.getInt(TAG_BURN_TIME);
    }

    @Override
    public boolean triggerEvent(int event, int param)
    {
        if (event == COOLDOWN_EVENT)
        {
            Entity e = null;
            if (level != null) {
                e = level.getEntity(param);
            }
            if (e != null)
            {
                e.level().addParticle(ParticleTypes.LARGE_SMOKE, e.getX(), e.getY() + 0.1, e.getZ(), 0.0D, 0.0D, 0.0D);
                e.level().addParticle(ParticleTypes.FLAME, e.getX(), e.getY(), e.getZ(), 0.0D, 0.0D, 0.0D);
            }
            return true;
        } else
        {
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
