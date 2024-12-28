package com.mcjinmouren.extrabotany.common.effect;

import com.mcjinmouren.extrabotany.common.effect.MobEffects.BloodTempation;
import com.mcjinmouren.extrabotany.common.effect.MobEffects.Reflect;
import com.mcjinmouren.extrabotany.common.lib.LibBrewNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.function.BiConsumer;

public class ExtraBotanyEffects {

    public static final MobEffect reflect = new Reflect();
    public static final MobEffect blood_temptation = new BloodTempation();

    public static void registerPotions(BiConsumer<MobEffect, ResourceLocation> r){
        r.accept(reflect, LibBrewNames.REFLECT);
        r.accept(blood_temptation, LibBrewNames.BLOOD_TEMPTATION);
    }

}
