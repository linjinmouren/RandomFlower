package com.mcjinmouren.extrabotany.common.Effects;

import com.mcjinmouren.extrabotany.Utils;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    private static final DeferredRegister<MobEffect> MOD_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Utils.MOD_ID);

    public static final RegistryObject<MobEffect> BLOOD_TEMPTATION = MOD_EFFECTS.register(
            Utils.BLOOD_TEMPTATION, BloodTempation::new);

    public static void register(IEventBus eventBus){
        MOD_EFFECTS.register(eventBus);
    }

}
