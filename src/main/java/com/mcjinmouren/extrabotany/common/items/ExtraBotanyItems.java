package com.mcjinmouren.extrabotany.common.items;

import com.mcjinmouren.extrabotany.ExtraBotany;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.xplat.XplatAbstractions;

public class ExtraBotanyItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExtraBotany.MOD_ID);

    public static final RegistryObject<Item> ORICHALCOS_INGOT =ITEMS.register(
            "orichalcos_ingot", ()-> new Item(new Item.Properties()));

    public static Item.Properties defaultBuilder() {
        return XplatAbstractions.INSTANCE.defaultItemBuilder();
    }

    public static void register(IEventBus eventBus){ITEMS.register(eventBus);}

}
