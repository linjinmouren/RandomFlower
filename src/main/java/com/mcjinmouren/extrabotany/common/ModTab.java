package com.mcjinmouren.extrabotany.common;

import com.mcjinmouren.extrabotany.Utils;
import com.mcjinmouren.extrabotany.common.Block.ModBlocks;
import com.mcjinmouren.extrabotany.common.Item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTab {

    public static final String MOD_TAB = "extrabotany.creativetab.mod_tab";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Utils.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_CREATIVETAB = CREATIVE_MODE_TABS.register(
            "mod_tab", ()-> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.ORICHALCOS_INGOT.get()))
                    .title(Component.translatable(MOD_TAB))
                    .displayItems((pParameters, pOutput) ->{
                        pOutput.accept(ModItems.ORICHALCOS_INGOT.get());
                        pOutput.accept(ModBlocks.bloodyenchantressFloating);
                    })
                    .build()
    );
}
