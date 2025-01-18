package com.mcjinmouren.randomflower.common.items.tools;

import com.mcjinmouren.randomflower.RandomFlower;
import com.mcjinmouren.randomflower.api.core.BlockPosDim;
import com.mcjinmouren.randomflower.api.core.PlayerChat;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

/**
 * This class defines the logic after using the Position Reader.
 * 该类定义了使用了坐标读取器后的逻辑。
 */

public class PositionReader extends Item {
    private static final String NBT_DIM = "dim";

    public PositionReader(Properties pProperties) {
        super(pProperties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        BlockPosDim dim = getPosition(stack);
        if (dim != null) {
            tooltip.add(Component.translatable(dim.toString()).withStyle(ChatFormatting.GRAY));
        }
        else {
            MutableComponent t = Component.translatable(getDescriptionId() + ".tooltip");
            t.withStyle(ChatFormatting.GRAY);
            tooltip.add(t);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        player.swing(hand);
        if (!player.isShiftKeyDown()) {
            if (player.level().isClientSide) {
                return InteractionResult.PASS;
            }
            BlockPos pos = context.getClickedPos();
            ItemStack held = player.getItemInHand(hand);
            BlockPosDim.setItemStackBlockPos(held, pos);
            held.getOrCreateTag().putString(NBT_DIM, BlockPosDim.dimensionToString(player.level()));
            PlayerChat.sendStatusMessage(player, PlayerChat.lang("message.randomflower.location_saved") + BlockPosDim.blockPosToString(pos));

            Vec3 vec = context.getClickLocation();
            held.getOrCreateTag().putDouble("hitx", vec.x - pos.getX());
            held.getOrCreateTag().putDouble("hity", vec.y - pos.getY());
            held.getOrCreateTag().putDouble("hitz", vec.z - pos.getZ());
        }
        return InteractionResult.SUCCESS;
    }

    public static BlockPosDim getPosition(ItemStack item) {
        BlockPos pos = BlockPosDim.getItemStackBlockPos(item);
        if (pos == null) {
            return null;
        }
        CompoundTag tag = item.getOrCreateTag();
        item.getHoverName();
        BlockPosDim dim = new BlockPosDim(pos, tag.getString(NBT_DIM), tag);
        try {
            Vec3 vec = new Vec3(
                    tag.getDouble("hitx"),
                    tag.getDouble("hity"),
                    tag.getDouble("hitz"));
            dim.setHitVec(vec);
        }
        catch (Exception e) {
            RandomFlower.LOGGER.error("SIde error in Read Position", e);
        }
        return dim;
    }
}
