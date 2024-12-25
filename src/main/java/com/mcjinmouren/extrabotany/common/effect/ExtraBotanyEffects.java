package com.mcjinmouren.extrabotany.common.effect;

import com.mcjinmouren.extrabotany.ExtraBotany;
import com.mcjinmouren.extrabotany.common.effect.MobEffects.BloodTempation;
import com.mcjinmouren.extrabotany.common.lib.LibBrewNames;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ExtraBotanyEffects {

    private static final DeferredRegister<MobEffect> MOD_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ExtraBotany.MOD_ID);

    public static final RegistryObject<MobEffect> BLOOD_TEMPTATION = MOD_EFFECTS.register(
            LibBrewNames.BLOOD_TEMPTATION, BloodTempation::new);

    public static void register(IEventBus eventBus){
        MOD_EFFECTS.register(eventBus);
    }

}
