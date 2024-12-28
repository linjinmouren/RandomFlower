package com.mcjinmouren.extrabotany.forge.datagen;

import com.mcjinmouren.extrabotany.ExtraBotany;
import com.mcjinmouren.extrabotany.common.items.ExtraBotanyItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class EnglishLangGen extends LanguageProvider {

    public EnglishLangGen(PackOutput output, String locale){
        super(output, ExtraBotany.MOD_ID, locale);
    }

    //It can't be used,and I don't know why
    @Override
    protected void addTranslations(){
    //    add(ExtraBotanyItems.ORICHALCOS_INGOT.get(), "Orichalcos Ingot");
    }

}
