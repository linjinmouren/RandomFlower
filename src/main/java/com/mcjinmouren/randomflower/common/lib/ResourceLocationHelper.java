package com.mcjinmouren.randomflower.common.lib;

import com.mcjinmouren.randomflower.RandomFlower;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationHelper {

    public ResourceLocationHelper(){
    }

    public static ResourceLocation prefix(String path){
        return new ResourceLocation(RandomFlower.MOD_ID, path);
    }

}
