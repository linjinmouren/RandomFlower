package com.mcjinmouren.extrabotany.forge.datagen;

import com.mcjinmouren.extrabotany.ExtraBotany;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ExtraBotany.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    //It can't be used,and I don't know why
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generators = event.getGenerator();
        PackOutput output = generators.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generators.addProvider(event.includeClient(), new EnglishLangGen(output, "en_us"));
    }

}
