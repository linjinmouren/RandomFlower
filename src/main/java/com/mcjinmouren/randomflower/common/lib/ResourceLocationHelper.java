package com.mcjinmouren.randomflower.common.lib;

import com.mcjinmouren.randomflower.RandomFlower;
import net.minecraft.resources.ResourceLocation;

/**
 * This class helps us write the resource file paths for the mod more conveniently.
 * 这个类帮助我们更方便地写Mod的资源文件路径。
 */

public class ResourceLocationHelper {

    public ResourceLocationHelper(){
    }

    public static ResourceLocation prefix(String path){
        return new ResourceLocation(RandomFlower.MOD_ID, path);
    }

}
