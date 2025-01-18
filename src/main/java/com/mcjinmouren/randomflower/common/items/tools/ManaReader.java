package com.mcjinmouren.randomflower.common.items.tools;

import com.mcjinmouren.randomflower.api.core.PlayerChat;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import vazkii.botania.api.block_entity.BindableSpecialFlowerBlockEntity;
import vazkii.botania.api.mana.ManaReceiver;

/**
 * This class defines the logic after using the Mana Reader.
 * 该类定义了使用了魔力读取器后的逻辑。
 */

public class ManaReader extends Item{

    public ManaReader(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        player.swing(hand);
        if (player.level().isClientSide) {
            return InteractionResult.PASS;
        }
        BlockEntity blockEntity= player.level().getBlockEntity(context.getClickedPos());
        if (blockEntity instanceof ManaReceiver manaReceiver){
            PlayerChat.sendStatusMessage(player, PlayerChat.lang("message.randomflower.mana_reader.read") + manaReceiver.getCurrentMana());
        }
        if (blockEntity instanceof BindableSpecialFlowerBlockEntity bindableSpecialFlowerBlockEntity)PlayerChat.sendStatusMessage(player, PlayerChat.lang("message.randomflower.mana_reader.read") +bindableSpecialFlowerBlockEntity.getMana());

        return InteractionResult.SUCCESS;
    }
}
