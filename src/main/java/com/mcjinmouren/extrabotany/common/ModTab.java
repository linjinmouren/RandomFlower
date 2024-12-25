package com.mcjinmouren.extrabotany.common;

import com.mcjinmouren.extrabotany.ExtraBotany;
import com.mcjinmouren.extrabotany.common.blocks.ExtraBotanyFlowerBlocks;
import com.mcjinmouren.extrabotany.common.items.ExtraBotanyItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTab {

    public static final String MOD_TAB = "extrabotany.creativetab.mod_tab";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExtraBotany.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_CREATIVETAB = CREATIVE_MODE_TABS.register(
            "mod_tab", ()-> CreativeModeTab.builder().icon(()-> new ItemStack(ExtraBotanyItems.ORICHALCOS_INGOT.get()))
                    .title(Component.translatable(MOD_TAB))
                    .displayItems((pParameters, pOutput) ->{
                        pOutput.accept(ExtraBotanyItems.ORICHALCOS_INGOT.get());
                        pOutput.accept(ExtraBotanyFlowerBlocks.bloodyenchantressFloating);
                    })
                    .build()
    );

    public static void register(IEventBus eventBus){CREATIVE_MODE_TABS.register(eventBus);}
}
