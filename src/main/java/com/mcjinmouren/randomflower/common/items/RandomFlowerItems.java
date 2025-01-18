package com.mcjinmouren.randomflower.common.items;

import com.mcjinmouren.randomflower.common.items.tools.ManaReader;
import com.mcjinmouren.randomflower.common.items.tools.PositionReader;
import com.mcjinmouren.randomflower.common.lib.LibItemNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.function.BiConsumer;

public class RandomFlowerItems {

    public static final Item position_reader = new PositionReader(defaultBuilder().stacksTo(1));
    public static final Item mana_reader = new ManaReader(defaultBuilder().stacksTo(1));

    public static void registerItems(BiConsumer<Item, ResourceLocation> r){
        r.accept(position_reader, LibItemNames.POSITION_READER);
        r.accept(mana_reader, LibItemNames.MANA_READER);
    }

    public static Item.Properties defaultBuilder() {
        return XplatAbstractions.INSTANCE.defaultItemBuilder();
    }

    /*
    *@Deprecated
    *public static final RegistryObject<Item> NIGHTMARE_FUEL = ITEMS.register("nightmare_fuel",
    *        ()->new NightmareFuel(new Item.Properties()));
    *@Deprecated
    *public static final RegistryObject<Item> SPIRIT_FUEL = ITEMS.register("spirit_fuel",
    *        ()-> new Item(new Item.Properties()));
    *@Deprecated
    *public static final RegistryObject<Item> SPIRIT_FRAGMENT = ITEMS.register("spirit_fragment",
    *        ()-> new Item(new Item.Properties()));
    *
    */




}
