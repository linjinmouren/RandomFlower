package com.mcjinmouren.randomflower.common;

import com.mcjinmouren.randomflower.RandomFlower;
import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import com.mcjinmouren.randomflower.common.items.RandomFlowerItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RandomFlowerCreativeModeTab {

    public static final String MOD_TAB = "randomflower.creativetab.mod_tab";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RandomFlower.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MOD_CREATIVETAB = CREATIVE_MODE_TABS.register(
            "mod_tab", ()-> CreativeModeTab.builder().icon(()-> new ItemStack(RandomFlowerFlowerBlocks.reikarlily))
                    .title(Component.translatable(MOD_TAB))
                    .displayItems((pParameters, pOutput) ->{
                        pOutput.accept(RandomFlowerFlowerBlocks.bloodyenchantressFloating);
                        pOutput.accept(RandomFlowerFlowerBlocks.bloodyenchantress);
                        pOutput.accept(RandomFlowerFlowerBlocks.bellflowerFloating);
                        pOutput.accept(RandomFlowerFlowerBlocks.bellflower);
                        pOutput.accept(RandomFlowerFlowerBlocks.reikarlilyFloating);
                        pOutput.accept(RandomFlowerFlowerBlocks.reikarlily);
                        pOutput.accept(RandomFlowerFlowerBlocks.mirrowtuniaFloating);
                        pOutput.accept(RandomFlowerFlowerBlocks.mirrowtunia);
                        pOutput.accept(RandomFlowerFlowerBlocks.omnivioletFloating);
                        pOutput.accept(RandomFlowerFlowerBlocks.omniviolet);
                        pOutput.accept(RandomFlowerFlowerBlocks.geminiorchidFloating);
                        pOutput.accept(RandomFlowerFlowerBlocks.geminiorchid);
                        pOutput.accept(RandomFlowerFlowerBlocks.enchantedorchidFloating);
                        pOutput.accept(RandomFlowerFlowerBlocks.enchantedorchid);
                        pOutput.accept(RandomFlowerFlowerBlocks.manalinkuim);
                        pOutput.accept(RandomFlowerFlowerBlocks.manalinkuimFloating);
                        pOutput.accept(RandomFlowerItems.POSITION_READER.get());
                        pOutput.accept(RandomFlowerItems.MANA_READER.get());
                        //pOutput.accept(RandomFlowerItems.NIGHTMARE_FUEL.get());
                        //pOutput.accept(RandomFlowerItems.SPIRIT_FUEL.get());
                        //pOutput.accept(RandomFlowerItems.SPIRIT_FRAGMENT.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus){CREATIVE_MODE_TABS.register(eventBus);}
}
