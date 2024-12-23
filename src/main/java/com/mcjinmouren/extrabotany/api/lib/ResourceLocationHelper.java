package com.mcjinmouren.extrabotany.api.lib;

import com.mcjinmouren.extrabotany.Utils;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationHelper {

    public ResourceLocationHelper(){
    }

    public static ResourceLocation prefix(String path){
        return new ResourceLocation(Utils.MOD_ID, path);
    }

}
