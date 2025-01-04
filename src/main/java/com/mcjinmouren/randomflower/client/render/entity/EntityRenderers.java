package com.mcjinmouren.randomflower.client.render.entity;

import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import vazkii.botania.client.render.block_entity.SpecialFlowerBlockEntityRenderer;

public class EntityRenderers {

    public interface EntityRendererConsumer {
        <E extends Entity> void accept(EntityType<? extends E> entityType,
                                       EntityRendererProvider<E> entityRendererFactory);
    }

    public interface BERConsumer {
        <E extends BlockEntity> void register(BlockEntityType<E> type, BlockEntityRendererProvider<? super E> factory);
    }

    public static void registerBlockEntityRenderers(BERConsumer consumer){
        consumer.register(RandomFlowerFlowerBlocks.BLOODYENCHANTRESS, SpecialFlowerBlockEntityRenderer::new);
        consumer.register(RandomFlowerFlowerBlocks.BELLFLOWER, SpecialFlowerBlockEntityRenderer::new);
        consumer.register(RandomFlowerFlowerBlocks.REIKARLILY, SpecialFlowerBlockEntityRenderer::new);
        consumer.register(RandomFlowerFlowerBlocks.MIRROWTUNIA, SpecialFlowerBlockEntityRenderer::new);
        consumer.register(RandomFlowerFlowerBlocks.OMNIVIOLET, SpecialFlowerBlockEntityRenderer::new);
        consumer.register(RandomFlowerFlowerBlocks.GEMINIORCHID, SpecialFlowerBlockEntityRenderer::new);
        consumer.register(RandomFlowerFlowerBlocks.ENCHANTEDORCHID, SpecialFlowerBlockEntityRenderer::new);
        consumer.register(RandomFlowerFlowerBlocks.MANALINKUIM, SpecialFlowerBlockEntityRenderer::new);
        consumer.register(RandomFlowerFlowerBlocks.MOONLIGHTLILY, SpecialFlowerBlockEntityRenderer::new);
    }

}
