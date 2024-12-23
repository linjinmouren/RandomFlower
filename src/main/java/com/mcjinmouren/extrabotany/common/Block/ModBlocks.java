package com.mcjinmouren.extrabotany.common.Block;

import com.mcjinmouren.extrabotany.Utils;
import com.mcjinmouren.extrabotany.common.Block.BlockEntity.GeneratingFlower.BloodyEnchantressBlockEntity;
import com.mcjinmouren.extrabotany.common.Item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.common.block.FloatingSpecialFlowerBlock;
import vazkii.botania.common.brew.BotaniaMobEffects;
import vazkii.botania.common.item.block.SpecialFlowerBlockItem;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModBlocks {

    private static final BlockBehaviour.Properties FLOWER_PROPS = BlockBehaviour.Properties.copy(Blocks.POPPY);
    private static final BlockBehaviour.Properties FLOATING_PROPS = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.5F).sound(SoundType.GRAVEL).lightLevel(s -> 15);

    public static final Block bloodyenchantress = createSpecialFlowerBlock(BotaniaMobEffects.soulCross, 300, FLOWER_PROPS, ()-> ModBlocks.BLOODYENCHANTRESS);
    public static final Block bloodyenchantressFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> ModBlocks.BLOODYENCHANTRESS);
    public static final Block bloodyenchantressPotted = flowerPot(bloodyenchantress, 0);

    public static final BlockEntityType<BloodyEnchantressBlockEntity> BLOODYENCHANTRESS = XplatAbstractions.INSTANCE.createBlockEntityType(BloodyEnchantressBlockEntity::new, bloodyenchantress, bloodyenchantressFloating);

    public static void registerBlocks(BiConsumer<Block, ResourceLocation> r){
    r.accept(bloodyenchantress, Utils.BLOODY_ENCHANTRESS);
    r.accept(bloodyenchantressFloating, floating(Utils.BLOODY_ENCHANTRESS));
    r.accept(bloodyenchantressPotted, potted(Utils.BLOODY_ENCHANTRESS));
    }

    public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r){
        Item.Properties props = ModItems.defaultBuilder();

        r.accept(new SpecialFlowerBlockItem(bloodyenchantress, props), getId(bloodyenchantress));
        r.accept(new SpecialFlowerBlockItem(bloodyenchantressFloating, props), getId(bloodyenchantressFloating));
    }

    public static void registerTEs(BiConsumer<BlockEntityType<?>, ResourceLocation> r){
        r.accept(BLOODYENCHANTRESS, getId(bloodyenchantress));
    }

    private static ResourceLocation getId(Block b) {
        return BuiltInRegistries.BLOCK.getKey(b);
    }

    private static ResourceLocation floating(ResourceLocation orig) {
        return new ResourceLocation(orig.getNamespace(), "floating_" + orig.getPath());
    }

    private static ResourceLocation potted(ResourceLocation orig) {
        return new ResourceLocation(orig.getNamespace(), "potted_" + orig.getPath());
    }

    static FlowerPotBlock flowerPot(Block block, int lightLevel) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        return new FlowerPotBlock(block, lightLevel > 0 ? properties.lightLevel(blockState -> lightLevel) : properties);
    }

    private static FlowerBlock createSpecialFlowerBlock(
            MobEffect effect, int effectDuration,
            BlockBehaviour.Properties props,
            Supplier<BlockEntityType<? extends SpecialFlowerBlockEntity>> beType) {
        return XplatAbstractions.INSTANCE.createSpecialFlowerBlock(
                effect, effectDuration, props, beType);
    }

/*
    private static final Item.Properties props = new Item.Properties( );
    private static final BlockBehaviour.Properties FLOATING_PROPS = BlockBehaviour.Properties.copy(Blocks.DIRT).strength(0.5F).sound(SoundType.GRAVEL).lightLevel(s -> 15);

    //generating
    public static final Block bloodyenchantress = createSpecialFlowerBlock(() -> ModBlocks.BLOODYENCHANTRESS);
    public static final Block bloodyenchantressFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()->ModBlocks.BLOODYENCHANTRESS);

    //block entity types
    public static final BlockEntityType<BloodyEnchantressBlockEntity> BLOODYENCHANTRESS = XplatAbstractions.INSTANCE.createBlockEntityType(BloodyEnchantressBlockEntity::new, bloodyenchantress, bloodyenchantressFloating);

    public static final Item bloodyenchantress_item = new SpecialFlowerBlockItem(bloodyenchantress, props);
    public static final Item bloodyenchantressFloating_item = new SpecialFlowerBlockItem(bloodyenchantressFloating, props);

    public static void registerBlocks(BiConsumer<Block, ResourceLocation> r){
    r.accept(bloodyenchantress, Utils.BLOODY_ENCHANTRESS);
    r.accept(bloodyenchantressFloating, floating(Utils.BLOODY_ENCHANTRESS));
    ExtraBotany.LOGGER.info("RegisterBlocks");
    }

    public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r){
    r.accept(bloodyenchantress_item, getId(bloodyenchantress));
    r.accept(bloodyenchantressFloating_item, getId(bloodyenchantressFloating));
    ExtraBotany.LOGGER.info("RegisterItems");
    }

    public static void registerWandHudCaps(BotaniaBlockEntities.BECapConsumer<WandHUD> consumer){
        consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((GeneratingFlowerBlockEntity) be),
                BLOODYENCHANTRESS);
    }

    public static void registerTEs(BiConsumer<BlockEntityType<?>, ResourceLocation> r){
        r.accept(BLOODYENCHANTRESS, getId(bloodyenchantress));
    }

    private static ResourceLocation getId(Block b)
    {
        return BuiltInRegistries.BLOCK.getKey(b);
    }

    private static ResourceLocation floating(ResourceLocation orig) {
        return new ResourceLocation(orig.getNamespace(), "floating_" + orig.getPath());
    }

    private static FlowerBlock createSpecialFlowerBlock(
            Supplier<BlockEntityType<? extends SpecialFlowerBlockEntity>> beType) {
        return XplatAbstractions.INSTANCE.createSpecialFlowerBlock(
                MobEffects.HARM, 114, ModBlocks.FLOATING_PROPS, beType);
    }
*/
}
