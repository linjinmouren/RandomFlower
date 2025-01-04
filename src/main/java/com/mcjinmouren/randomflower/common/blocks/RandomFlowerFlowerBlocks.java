package com.mcjinmouren.randomflower.common.blocks;

import com.mcjinmouren.randomflower.common.blocks.flower.functional.EnchantedOrchidBlockEntity;
import com.mcjinmouren.randomflower.common.blocks.flower.functional.ManalinkuimBlockEntity;
import com.mcjinmouren.randomflower.common.blocks.flower.functional.MirrowtuniaBlockEntity;
import com.mcjinmouren.randomflower.common.blocks.flower.generating.*;
import com.mcjinmouren.randomflower.common.effect.RandomFlowerEffects;
import com.mcjinmouren.randomflower.common.items.RandomFlowerItems;
import com.mcjinmouren.randomflower.common.lib.LibBlockNames;
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

public class RandomFlowerFlowerBlocks {

    private static final BlockBehaviour.Properties FLOWER_PROPS = BlockBehaviour.Properties.copy(Blocks.POPPY);
    private static final BlockBehaviour.Properties FLOATING_PROPS = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.5F).sound(SoundType.GRAVEL).lightLevel(s -> 15);

    /*
    @
    @   Part of generating flower 产能花部分
    @
     */
    //Bloody Enchantress 鲜血妖姬
    public static final Block bloodyenchantress = createSpecialFlowerBlock(BotaniaMobEffects.soulCross, 300, FLOWER_PROPS, ()-> RandomFlowerFlowerBlocks.BLOODYENCHANTRESS);
    public static final Block bloodyenchantressFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> RandomFlowerFlowerBlocks.BLOODYENCHANTRESS);
    public static final Block bloodyenchantressPotted = flowerPot(bloodyenchantress, 0);
    public static final BlockEntityType<BloodyEnchantressBlockEntity> BLOODYENCHANTRESS = XplatAbstractions.INSTANCE.createBlockEntityType(BloodyEnchantressBlockEntity::new, bloodyenchantress, bloodyenchantressFloating);
    //Bell Flower 风铃草
    public static final Block bellflower = createSpecialFlowerBlock(BotaniaMobEffects.featherfeet, 120, FLOWER_PROPS, ()-> RandomFlowerFlowerBlocks.BELLFLOWER);
    public static final Block bellflowerFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> RandomFlowerFlowerBlocks.BELLFLOWER);
    public static final Block bellflowerPotted = flowerPot(bellflower, 0);
    public static final BlockEntityType<BellFlowerBlockEntity> BELLFLOWER = XplatAbstractions.INSTANCE.createBlockEntityType(BellFlowerBlockEntity::new, bellflower, bellflowerFloating);
    //ReiKar Lily 雷卡兰
    public static final Block reikarlily = createSpecialFlowerBlock(MobEffects.FIRE_RESISTANCE, 240, FLOWER_PROPS, ()-> RandomFlowerFlowerBlocks.REIKARLILY);
    public static final Block reikarlilyFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> RandomFlowerFlowerBlocks.REIKARLILY);
    public static final Block reikarlilyPotted = flowerPot(reikarlily, 0);
    public static final BlockEntityType<ReikarLilyBlockEntity> REIKARLILY = XplatAbstractions.INSTANCE.createBlockEntityType(ReikarLilyBlockEntity::new, reikarlily, reikarlilyFloating);
    //Omni Violet 全知瑾
    public static final Block omniviolet = createSpecialFlowerBlock(MobEffects.LUCK, 120, FLOWER_PROPS, ()-> RandomFlowerFlowerBlocks.OMNIVIOLET);
    public static final Block omnivioletFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> RandomFlowerFlowerBlocks.OMNIVIOLET);
    public static final Block omnivioletPotted = flowerPot(omniviolet, 0);
    public static final BlockEntityType<OmniVioletBlockEntity> OMNIVIOLET = XplatAbstractions.INSTANCE.createBlockEntityType(OmniVioletBlockEntity::new, omniviolet, omnivioletFloating);
    //Gemini Orchid 双子兰
    public static final Block geminiorchid = createSpecialFlowerBlock(MobEffects.LUCK, 120, FLOWER_PROPS, ()-> RandomFlowerFlowerBlocks.GEMINIORCHID);
    public static final Block geminiorchidFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> RandomFlowerFlowerBlocks.GEMINIORCHID);
    public static final Block geminiorchidPotted = flowerPot(geminiorchid, 0);
    public static final BlockEntityType<GeminiOrchidBlockEntity> GEMINIORCHID = XplatAbstractions.INSTANCE.createBlockEntityType(GeminiOrchidBlockEntity::new, geminiorchid, geminiorchidFloating);
    //Moonlight Lily 月光百合
    public static final Block moonlightlily = createSpecialFlowerBlock(MobEffects.LUCK, 120, FLOWER_PROPS, ()-> RandomFlowerFlowerBlocks.MOONLIGHTLILY);
    public static final Block moonlighylilyFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> RandomFlowerFlowerBlocks.MOONLIGHTLILY);
    public static final Block moonlightlilyPotted = flowerPot(moonlightlily, 0);
    public static final BlockEntityType<MoonlightLilyBlockEntity> MOONLIGHTLILY = XplatAbstractions.INSTANCE.createBlockEntityType(MoonlightLilyBlockEntity::new, moonlightlily, moonlighylilyFloating);
    /*
    @
    @   Part of function flower 功能花部分
    @
     */
    //Mirrowtunia 镜姬
    public static final Block mirrowtunia = createSpecialFlowerBlock(RandomFlowerEffects.reflect, 120, FLOWER_PROPS, ()-> RandomFlowerFlowerBlocks.MIRROWTUNIA);
    public static final Block mirrowtuniaFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> RandomFlowerFlowerBlocks.MIRROWTUNIA);
    public static final Block mirrowtuniaPotted = flowerPot(mirrowtunia,0);
    public static final BlockEntityType<MirrowtuniaBlockEntity> MIRROWTUNIA = XplatAbstractions.INSTANCE.createBlockEntityType(MirrowtuniaBlockEntity::new, mirrowtunia, mirrowtuniaFloating);
    //Enchanted Orchid 蕴魔瑾
    public static final Block enchantedorchid = createSpecialFlowerBlock(MobEffects.LUCK, 180 , FLOWER_PROPS, ()-> RandomFlowerFlowerBlocks.ENCHANTEDORCHID);
    public static final Block enchantedorchidFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> RandomFlowerFlowerBlocks.ENCHANTEDORCHID);
    public static final Block enchantedorchidPotted = flowerPot(enchantedorchid, 0);
    public static final BlockEntityType<EnchantedOrchidBlockEntity> ENCHANTEDORCHID = XplatAbstractions.INSTANCE.createBlockEntityType(EnchantedOrchidBlockEntity::new, enchantedorchid, enchantedorchidFloating);
    //ManaLinkuim 魔链星
    public static final Block manalinkuim = createSpecialFlowerBlock(MobEffects.LUCK, 180, FLOWER_PROPS, ()-> RandomFlowerFlowerBlocks.MANALINKUIM);
    public static final Block manalinkuimFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> RandomFlowerFlowerBlocks.MANALINKUIM);
    public static final Block manalinkuimPotted = flowerPot(manalinkuim, 0);
    public static final BlockEntityType<ManalinkuimBlockEntity> MANALINKUIM = XplatAbstractions.INSTANCE.createBlockEntityType(ManalinkuimBlockEntity::new, manalinkuim, manalinkuimFloating);

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
    //Omni Violet 全知瑾
    r.accept(omniviolet, LibBlockNames.OMNIVIOLET);
    r.accept(omnivioletFloating, floating(LibBlockNames.OMNIVIOLET));
    r.accept(omnivioletPotted, potted(LibBlockNames.OMNIVIOLET));
    //Gemini Orchid 双子兰
    r.accept(geminiorchid, LibBlockNames.GEMINIORCHID);
    r.accept(geminiorchidFloating, floating(LibBlockNames.GEMINIORCHID));
    r.accept(geminiorchidPotted, potted(LibBlockNames.GEMINIORCHID));
    //Mirrowtunia 镜姬
    r.accept(mirrowtunia, LibBlockNames.MIRROWTUNIA);
    r.accept(mirrowtuniaFloating, floating(LibBlockNames.MIRROWTUNIA));
    r.accept(mirrowtuniaPotted, potted(LibBlockNames.MIRROWTUNIA));
    //Enchanted Orchid 蕴魔瑾
    r.accept(enchantedorchid, LibBlockNames.ENCHANTEDORCHID);
    r.accept(enchantedorchidFloating, floating(LibBlockNames.ENCHANTEDORCHID));
    r.accept(enchantedorchidPotted, potted(LibBlockNames.ENCHANTEDORCHID));
    ////ManaLinkuim 魔链星
    r.accept(manalinkuim, LibBlockNames.MANALINKUIM);
    r.accept(manalinkuimFloating, floating(LibBlockNames.MANALINKUIM));
    r.accept(manalinkuimPotted, potted(LibBlockNames.MANALINKUIM));
    //Moonlight Lily 月光百合
    r.accept(moonlightlily, LibBlockNames.MOONLIGHTLILY);
    r.accept(moonlighylilyFloating, floating(LibBlockNames.MOONLIGHTLILY));
    r.accept(moonlightlilyPotted, potted(LibBlockNames.MOONLIGHTLILY));
    }

    public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r){
        Item.Properties props = RandomFlowerItems.defaultBuilder();

        r.accept(new SpecialFlowerBlockItem(bloodyenchantress, props), getId(bloodyenchantress));
        r.accept(new SpecialFlowerBlockItem(bloodyenchantressFloating, props), getId(bloodyenchantressFloating));

        r.accept(new SpecialFlowerBlockItem(bellflower, props), getId(bellflower));
        r.accept(new SpecialFlowerBlockItem(bellflowerFloating, props), getId(bellflowerFloating));

        r.accept(new SpecialFlowerBlockItem(reikarlily, props), getId(reikarlily));
        r.accept(new SpecialFlowerBlockItem(reikarlilyFloating, props), getId(reikarlilyFloating));

        r.accept(new SpecialFlowerBlockItem(mirrowtunia, props), getId(mirrowtunia));
        r.accept(new SpecialFlowerBlockItem(mirrowtuniaFloating, props), getId(mirrowtuniaFloating));

        r.accept(new SpecialFlowerBlockItem(omniviolet, props), getId(omniviolet));
        r.accept(new SpecialFlowerBlockItem(omnivioletFloating, props), getId(omnivioletFloating));

        r.accept(new SpecialFlowerBlockItem(geminiorchid, props), getId(geminiorchid));
        r.accept(new SpecialFlowerBlockItem(geminiorchidFloating, props), getId(geminiorchidFloating));

        r.accept(new SpecialFlowerBlockItem(enchantedorchid, props), getId(enchantedorchid));
        r.accept(new SpecialFlowerBlockItem(enchantedorchidFloating, props), getId(enchantedorchidFloating));

        r.accept(new SpecialFlowerBlockItem(manalinkuim, props), getId(manalinkuim));
        r.accept(new SpecialFlowerBlockItem(manalinkuimFloating, props), getId(manalinkuimFloating));

        r.accept(new SpecialFlowerBlockItem(moonlightlily, props), getId(moonlightlily));
        r.accept(new SpecialFlowerBlockItem(moonlighylilyFloating, props), getId(moonlighylilyFloating));
    }

    public static void registerTEs(BiConsumer<BlockEntityType<?>, ResourceLocation> r){
        r.accept(BLOODYENCHANTRESS, getId(bloodyenchantress));
        r.accept(BELLFLOWER, getId(bellflower));
        r.accept(REIKARLILY, getId(reikarlily));
        r.accept(MIRROWTUNIA, getId(mirrowtunia));
        r.accept(OMNIVIOLET, getId(omniviolet));
        r.accept(GEMINIORCHID, getId(geminiorchid));
        r.accept(ENCHANTEDORCHID, getId(enchantedorchid));
        r.accept(MANALINKUIM, getId(manalinkuim));
        r.accept(MOONLIGHTLILY, getId(moonlightlily));
    }

    public static void registerWandHudCaps(BotaniaBlockEntities.BECapConsumer<WandHUD> consumer){
        consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((GeneratingFlowerBlockEntity) be),
                BLOODYENCHANTRESS, BELLFLOWER, REIKARLILY, OMNIVIOLET, GEMINIORCHID, MOONLIGHTLILY);
        consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((FunctionalFlowerBlockEntity) be),
                MIRROWTUNIA, ENCHANTEDORCHID, MANALINKUIM);
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
