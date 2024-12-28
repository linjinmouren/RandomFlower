package com.mcjinmouren.extrabotany.client.render.entity;

import com.mcjinmouren.extrabotany.common.blocks.ExtraBotanyFlowerBlocks;
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
        consumer.register(ExtraBotanyFlowerBlocks.BLOODYENCHANTRESS, SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyFlowerBlocks.BELLFLOWER, SpecialFlowerBlockEntityRenderer::new);
        consumer.register(ExtraBotanyFlowerBlocks.REIKARLILY, SpecialFlowerBlockEntityRenderer::new);
    }

}
