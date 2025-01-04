package com.mcjinmouren.randomflower.common.items;

import com.mcjinmouren.randomflower.RandomFlower;
import com.mcjinmouren.randomflower.common.items.fuel.NightmareFuel;
import com.mcjinmouren.randomflower.common.items.tools.ManaReader;
import com.mcjinmouren.randomflower.common.items.tools.PositionReader;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vazkii.botania.xplat.XplatAbstractions;

public class RandomFlowerItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RandomFlower.MOD_ID);

    public static final RegistryObject<Item> POSITION_READER = ITEMS.register("position_reader",
            ()-> new PositionReader(new Item.Properties().stacksTo(1)));
    
    public static final RegistryObject<Item> MANA_READER = ITEMS.register("mana_reader",
            ()->new ManaReader(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> NIGHTMARE_FUEL = ITEMS.register("nightmare_fuel",
            ()->new NightmareFuel(new Item.Properties()));

    public static final RegistryObject<Item> SPIRIT_FUEL = ITEMS.register("spirit_fuel",
            ()-> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPIRIT_FRAGMENT = ITEMS.register("spirit_fragment",
            ()-> new Item(new Item.Properties()));

    public static Item.Properties defaultBuilder() {
        return XplatAbstractions.INSTANCE.defaultItemBuilder();
    }

    public static void register(IEventBus eventBus){ITEMS.register(eventBus);}
}
