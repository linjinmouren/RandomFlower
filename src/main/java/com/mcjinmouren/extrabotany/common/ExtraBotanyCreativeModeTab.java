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

public class ExtraBotanyCreativeModeTab {

    public static final String MOD_TAB = "extrabotany.creativetab.mod_tab";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExtraBotany.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_CREATIVETAB = CREATIVE_MODE_TABS.register(
            "mod_tab", ()-> CreativeModeTab.builder().icon(()-> new ItemStack(ExtraBotanyFlowerBlocks.reikarlily))
                    .title(Component.translatable(MOD_TAB))
                    .displayItems((pParameters, pOutput) ->{
                        pOutput.accept(ExtraBotanyFlowerBlocks.bloodyenchantressFloating);
                        pOutput.accept(ExtraBotanyFlowerBlocks.bloodyenchantress);
                        pOutput.accept(ExtraBotanyFlowerBlocks.bellflowerFloating);
                        pOutput.accept(ExtraBotanyFlowerBlocks.bellflower);
                        pOutput.accept(ExtraBotanyFlowerBlocks.reikarlilyFloating);
                        pOutput.accept(ExtraBotanyFlowerBlocks.reikarlily);
                        pOutput.accept(ExtraBotanyFlowerBlocks.mirrowtuniaFloating);
                        pOutput.accept(ExtraBotanyFlowerBlocks.mirrowtunia);
                        pOutput.accept(ExtraBotanyFlowerBlocks.omnivioletFloating);
                        pOutput.accept(ExtraBotanyFlowerBlocks.omniviolet);
                        pOutput.accept(ExtraBotanyItems.POSITION_READER.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus){CREATIVE_MODE_TABS.register(eventBus);}
}
