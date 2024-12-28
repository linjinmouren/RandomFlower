package com.mcjinmouren.extrabotany.common.blocks;

import com.mcjinmouren.extrabotany.common.blocks.flower.functional.MirrowtuniaBlockEntity;
import com.mcjinmouren.extrabotany.common.blocks.flower.generating.BellFlowerBlockEntity;
import com.mcjinmouren.extrabotany.common.blocks.flower.generating.BloodyEnchantressBlockEntity;
import com.mcjinmouren.extrabotany.common.blocks.flower.generating.ReikarLilyBlockEntity;
import com.mcjinmouren.extrabotany.common.effect.ExtraBotanyEffects;
import com.mcjinmouren.extrabotany.common.items.ExtraBotanyItems;
import com.mcjinmouren.extrabotany.common.lib.LibBlockNames;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.api.block_entity.BindableSpecialFlowerBlockEntity;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.common.block.FloatingSpecialFlowerBlock;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;
import vazkii.botania.common.brew.BotaniaMobEffects;
import vazkii.botania.common.item.block.SpecialFlowerBlockItem;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ExtraBotanyFlowerBlocks {

    private static final BlockBehaviour.Properties FLOWER_PROPS = BlockBehaviour.Properties.copy(Blocks.POPPY);
    private static final BlockBehaviour.Properties FLOATING_PROPS = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.5F).sound(SoundType.GRAVEL).lightLevel(s -> 15);

    /*
    @
    @   Part of generating flower 产能花部分
    @
     */
    //Bloody Enchantress 鲜血妖姬
    public static final Block bloodyenchantress = createSpecialFlowerBlock(BotaniaMobEffects.soulCross, 300, FLOWER_PROPS, ()-> ExtraBotanyFlowerBlocks.BLOODYENCHANTRESS);
    public static final Block bloodyenchantressFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> ExtraBotanyFlowerBlocks.BLOODYENCHANTRESS);
    public static final Block bloodyenchantressPotted = flowerPot(bloodyenchantress, 0);
    public static final BlockEntityType<BloodyEnchantressBlockEntity> BLOODYENCHANTRESS = XplatAbstractions.INSTANCE.createBlockEntityType(BloodyEnchantressBlockEntity::new, bloodyenchantress, bloodyenchantressFloating);
    //Bell Flower 风铃草
    public static final Block bellflower = createSpecialFlowerBlock(BotaniaMobEffects.featherfeet, 120, FLOWER_PROPS, ()->ExtraBotanyFlowerBlocks.BELLFLOWER);
    public static final Block bellflowerFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> ExtraBotanyFlowerBlocks.BELLFLOWER);
    public static final Block bellflowerPotted = flowerPot(bellflower, 0);
    public static final BlockEntityType<BellFlowerBlockEntity> BELLFLOWER = XplatAbstractions.INSTANCE.createBlockEntityType(BellFlowerBlockEntity::new, bellflower, bellflowerFloating);
    //ReiKar Lily 雷卡兰
    public static final Block reikarlily = createSpecialFlowerBlock(MobEffects.FIRE_RESISTANCE, 240, FLOWER_PROPS, ()->ExtraBotanyFlowerBlocks.REIKARLILY);
    public static final Block reikarlilyFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> ExtraBotanyFlowerBlocks.REIKARLILY);
    public static final Block reikarlilyPotted = flowerPot(reikarlily, 0);
    public static final BlockEntityType<ReikarLilyBlockEntity> REIKARLILY = XplatAbstractions.INSTANCE.createBlockEntityType(ReikarLilyBlockEntity::new, reikarlily, reikarlilyFloating);

    /*
    @
    @   Part of function flower 功能花部分
    @
     */
    //Mirrowtunia 镜姬
    public static final Block mirrowtunia = createSpecialFlowerBlock(ExtraBotanyEffects.reflect, 120, FLOWER_PROPS, ()->ExtraBotanyFlowerBlocks.MIRROWTUNIA);
    public static final Block mirrowtuniaFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> ExtraBotanyFlowerBlocks.MIRROWTUNIA);
    public static final Block mirrowtuniaPotted = flowerPot(mirrowtunia,0);
    public static final BlockEntityType<MirrowtuniaBlockEntity> MIRROWTUNIA = XplatAbstractions.INSTANCE.createBlockEntityType(MirrowtuniaBlockEntity::new, mirrowtunia, mirrowtuniaFloating);


    public static void registerBlocks(BiConsumer<Block, ResourceLocation> r){
    //Bloody Enchantress 鲜血妖姬
    r.accept(bloodyenchantress, LibBlockNames.BLOODY_ENCHANTRESS);
    r.accept(bloodyenchantressFloating, floating(LibBlockNames.BLOODY_ENCHANTRESS));
    r.accept(bloodyenchantressPotted, potted(LibBlockNames.BLOODY_ENCHANTRESS));
    //Bell Flower 风铃草
    r.accept(bellflower, LibBlockNames.BELL_FLOWER);
    r.accept(bellflowerFloating, floating(LibBlockNames.BELL_FLOWER));
    r.accept(bellflowerPotted, potted(LibBlockNames.BELL_FLOWER));
    //ReiKar Lily 雷卡兰
    r.accept(reikarlily, LibBlockNames.REIKAR_LILY);
    r.accept(reikarlilyFloating, floating(LibBlockNames.REIKAR_LILY));
    r.accept(reikarlilyPotted, potted(LibBlockNames.REIKAR_LILY));
    //Mirrowtunia 镜姬
    r.accept(mirrowtunia, LibBlockNames.MIRROWTUNIA);
    r.accept(mirrowtuniaFloating, floating(LibBlockNames.MIRROWTUNIA));
    r.accept(mirrowtuniaPotted, potted(LibBlockNames.MIRROWTUNIA));
    }

    public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r){
        Item.Properties props = ExtraBotanyItems.defaultBuilder();

        r.accept(new SpecialFlowerBlockItem(bloodyenchantress, props), getId(bloodyenchantress));
        r.accept(new SpecialFlowerBlockItem(bloodyenchantressFloating, props), getId(bloodyenchantressFloating));

        r.accept(new SpecialFlowerBlockItem(bellflower, props), getId(bellflower));
        r.accept(new SpecialFlowerBlockItem(bellflowerFloating, props), getId(bellflowerFloating));

        r.accept(new SpecialFlowerBlockItem(reikarlily, props), getId(reikarlily));
        r.accept(new SpecialFlowerBlockItem(reikarlilyFloating, props), getId(reikarlilyFloating));

        r.accept(new SpecialFlowerBlockItem(mirrowtunia, props), getId(mirrowtunia));
        r.accept(new SpecialFlowerBlockItem(mirrowtuniaFloating, props), getId(mirrowtuniaFloating));
    }

    public static void registerTEs(BiConsumer<BlockEntityType<?>, ResourceLocation> r){
        r.accept(BLOODYENCHANTRESS, getId(bloodyenchantress));
        r.accept(BELLFLOWER, getId(bellflower));
        r.accept(REIKARLILY, getId(reikarlily));
        r.accept(MIRROWTUNIA, getId(mirrowtunia));
    }

    public static void registerWandHudCaps(BotaniaBlockEntities.BECapConsumer<WandHUD> consumer){
        consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((GeneratingFlowerBlockEntity) be),
                BLOODYENCHANTRESS, BELLFLOWER, REIKARLILY);
        consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((FunctionalFlowerBlockEntity) be),
                MIRROWTUNIA);
    }

    public static void registerFlowerPotPlants(BiConsumer<ResourceLocation, Supplier<? extends Block>> consumer) {
        registerBlocks((block, resourceLocation) -> {
            if (block instanceof FlowerPotBlock) {
                var id = getId(block);
                consumer.accept(new ResourceLocation(id.getNamespace(), id.getPath().substring(LibBlockNames.POTTED_PREFIX.length())), () -> block);
            }
        });
    }

    private static ResourceLocation floating(ResourceLocation orig) {
        return new ResourceLocation(orig.getNamespace(), "floating_" + orig.getPath());
    }

    private static ResourceLocation potted(ResourceLocation orig) {
        return new ResourceLocation(orig.getNamespace(), "potted_" + orig.getPath());
    }


    private static ResourceLocation getId(Block b) {
        return BuiltInRegistries.BLOCK.getKey(b);
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
}
