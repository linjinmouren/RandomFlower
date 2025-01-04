package com.mcjinmouren.randomflower.common.effect;

import com.mcjinmouren.randomflower.common.effect.MobEffects.BloodTempation;
import com.mcjinmouren.randomflower.common.effect.MobEffects.Reflect;
import com.mcjinmouren.randomflower.common.lib.LibBrewNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.function.BiConsumer;

public class RandomFlowerEffects {

    public static final MobEffect reflect = new Reflect();
    public static final MobEffect blood_temptation = new BloodTempation();

    public static void registerPotions(BiConsumer<MobEffect, ResourceLocation> r){
        r.accept(reflect, LibBrewNames.REFLECT);
        r.accept(blood_temptation, LibBrewNames.BLOOD_TEMPTATION);
    }

}
