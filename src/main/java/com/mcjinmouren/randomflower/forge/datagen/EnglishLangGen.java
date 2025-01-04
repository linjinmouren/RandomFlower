package com.mcjinmouren.randomflower.forge.datagen;

import com.mcjinmouren.randomflower.RandomFlower;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class EnglishLangGen extends LanguageProvider {

    public EnglishLangGen(PackOutput output, String locale){
        super(output, RandomFlower.MOD_ID, locale);
    }

    //It can't be used,and I don't know why
    @Override
    protected void addTranslations(){
    //    add(RandomFlowerItems.ORICHALCOS_INGOT.get(), "Orichalcos Ingot");
    }

}
