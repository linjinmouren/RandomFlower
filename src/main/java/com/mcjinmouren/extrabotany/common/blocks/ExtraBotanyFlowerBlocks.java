package com.mcjinmouren.extrabotany.common.blocks;

import com.mcjinmouren.extrabotany.common.blocks.flower.generating.BloodyEnchantressBlockEntity;
import com.mcjinmouren.extrabotany.common.items.ExtraBotanyItems;
import com.mcjinmouren.extrabotany.common.lib.LibBlockNames;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.api.block_entity.BindableSpecialFlowerBlockEntity;
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

    public static final Block bloodyenchantress = createSpecialFlowerBlock(()-> ExtraBotanyFlowerBlocks.BLOODYENCHANTRESS);
    public static final Block bloodyenchantressFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, ()-> ExtraBotanyFlowerBlocks.BLOODYENCHANTRESS);
    public static final Block bloodyenchantressPotted = flowerPot();

    public static final BlockEntityType<BloodyEnchantressBlockEntity> BLOODYENCHANTRESS = XplatAbstractions.INSTANCE.createBlockEntityType(BloodyEnchantressBlockEntity::new, bloodyenchantress, bloodyenchantressFloating);

    public static void registerBlocks(BiConsumer<Block, ResourceLocation> r){
    r.accept(bloodyenchantress, LibBlockNames.BLOODY_ENCHANTRESS);
    r.accept(bloodyenchantressFloating, floating());
    r.accept(bloodyenchantressPotted, potted());
    }

    public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r){
        Item.Properties props = ExtraBotanyItems.defaultBuilder();

        r.accept(new SpecialFlowerBlockItem(bloodyenchantress, props), getId(bloodyenchantress));
        r.accept(new SpecialFlowerBlockItem(bloodyenchantressFloating, props), getId(bloodyenchantressFloating));
    }

    public static void registerTEs(BiConsumer<BlockEntityType<?>, ResourceLocation> r){
        r.accept(BLOODYENCHANTRESS, getId(bloodyenchantress));
    }

    public static void registerWandHudCaps(BotaniaBlockEntities.BECapConsumer<WandHUD> consumer){
        consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((GeneratingFlowerBlockEntity) be),
                BLOODYENCHANTRESS);
    }

    public static void registerFlowerPotPlants(BiConsumer<ResourceLocation, Supplier<? extends Block>> consumer) {
        registerBlocks((block, resourceLocation) -> {
            if (block instanceof FlowerPotBlock) {
                var id = getId(block);
                consumer.accept(new ResourceLocation(id.getNamespace(), id.getPath().substring(LibBlockNames.POTTED_PREFIX.length())), () -> block);
            }
        });
    }

    private static ResourceLocation getId(Block b) {
        return BuiltInRegistries.BLOCK.getKey(b);
    }

    private static ResourceLocation floating() {
        return new ResourceLocation(LibBlockNames.BLOODY_ENCHANTRESS.getNamespace(), "floating_" + LibBlockNames.BLOODY_ENCHANTRESS.getPath());
    }

    private static ResourceLocation potted() {
        return new ResourceLocation(LibBlockNames.BLOODY_ENCHANTRESS.getNamespace(), "potted_" + LibBlockNames.BLOODY_ENCHANTRESS.getPath());
    }

    static FlowerPotBlock flowerPot() {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        return new FlowerPotBlock(ExtraBotanyFlowerBlocks.bloodyenchantress, 0 > 0 ? properties.lightLevel(blockState -> 0) : properties);
    }

    private static FlowerBlock createSpecialFlowerBlock(
            Supplier<BlockEntityType<? extends SpecialFlowerBlockEntity>> beType) {
        return XplatAbstractions.INSTANCE.createSpecialFlowerBlock(
                BotaniaMobEffects.soulCross, 300, ExtraBotanyFlowerBlocks.FLOWER_PROPS, beType);
    }
}
