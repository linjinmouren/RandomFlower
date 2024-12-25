package com.mcjinmouren.extrabotany.common.lib;

import com.mcjinmouren.extrabotany.ExtraBotany;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationHelper {

    public ResourceLocationHelper(){
    }

    public static ResourceLocation prefix(String path){
        return new ResourceLocation(ExtraBotany.MOD_ID, path);
    }

}
