/*
*
*   This flower can now work without a multi-block structure;
*   perhaps a multi-block structure may be added in the future?
*
*/
package com.mcjinmouren.randomflower.common.blocks.flower.functional;

import com.mcjinmouren.randomflower.api.core.BlockPosDim;
import com.mcjinmouren.randomflower.api.core.PlayerChat;
import com.mcjinmouren.randomflower.common.blocks.RandomFlowerFlowerBlocks;
import com.mcjinmouren.randomflower.common.items.tools.PositionReader;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;

import java.util.HashMap;
import java.util.Objects;

public class ManalinkuimBlockEntity extends FunctionalFlowerBlockEntity {

    private static final String TAG_X = "x";
    private static final String TAG_Y = "y";
    private static final String TAG_Z = "z";

    static BlockPos pos;
    private int sleep;
    private int noSleep;
    BlockEntity blockEntity;
    private static final HashMap<BlockPos, Object> structure= new HashMap<>();
    static {
        for (int i = -2; i <= 2; i++) {
            structure.put(new BlockPos(i,-1,-2),Blocks.QUARTZ_BLOCK);
            structure.put(new BlockPos(i,-1,2),Blocks.QUARTZ_BLOCK);
            structure.put(new BlockPos(2,-1,i),Blocks.QUARTZ_BLOCK);
            structure.put(new BlockPos(-2,-1,i),Blocks.QUARTZ_BLOCK);
        }
        structure.put(new BlockPos(-1,-1,0), Blocks.OBSIDIAN);
        structure.put(new BlockPos(1,-1,0), Blocks.OBSIDIAN);
        structure.put(new BlockPos(0,-1,1), Blocks.OBSIDIAN);
        structure.put(new BlockPos(0,-1,-1), Blocks.OBSIDIAN);
        structure.put(new BlockPos(1,-1,1),Blocks.LAPIS_BLOCK);
        structure.put(new BlockPos(1,-1,-1),Blocks.LAPIS_BLOCK);
        structure.put(new BlockPos(-1,-1,1),Blocks.LAPIS_BLOCK);
        structure.put(new BlockPos(-1,-1,-1),Blocks.LAPIS_BLOCK);
    }
    @SubscribeEvent
    public static void onPlayerUseItem(PlayerInteractEvent.RightClickBlock event){
        Player player = event.getEntity();
        ItemStack item = player.getMainHandItem();
        BlockEntity blockEntity = event.getLevel().getBlockEntity(event.getHitVec().getBlockPos());
        if (item.getItem() instanceof PositionReader && blockEntity instanceof ManalinkuimBlockEntity && player.isShiftKeyDown()){
            BlockPos posr = BlockPosDim.getItemStackBlockPos(item);
            if (posr != null) {
                CompoundTag compoundTag = blockEntity.getPersistentData();
                compoundTag.putInt(TAG_X,posr.getX());
                compoundTag.putInt(TAG_Y,posr.getY());
                compoundTag.putInt(TAG_Z,posr.getZ());
                pos = new BlockPos(posr.getX(),posr.getY(),posr.getZ());
                PlayerChat.sendStatusMessage(player, PlayerChat.lang("message.randomflower.position_written")+BlockPosDim.blockPosToString(pos));
            }
        }
    }
    private void upDataPos(){
        if (level != null) {
            CompoundTag tag = Objects.requireNonNull(level.getBlockEntity(getBlockPos())).getPersistentData();
            pos = new BlockPos(tag.getInt(TAG_X), tag.getInt(TAG_Y), tag.getInt(TAG_Z));
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        upDataPos();
    }

    public ManalinkuimBlockEntity(BlockPos pos, BlockState state) {
        super(RandomFlowerFlowerBlocks.MANALINKUIM, pos, state);
    }
    public void tickFlower() {
        super.tickFlower();
        if (sleep>0){sleep--;return;}
        if(noSleep-- >0&&!new BlockPosDim(getBlockPos(),level).checkStructure(structure)){sleep+=10;return;}else noSleep+=40;
        if (level != null && level.isLoaded(pos)) {
            blockEntity = level.getBlockEntity(pos);
        }
        if (blockEntity instanceof ManaPoolBlockEntity manaReceiver) {
            if (!manaReceiver.isFull()) {
                int CanReceive = manaReceiver.getMaxMana() - manaReceiver.getCurrentMana();
                if (CanReceive<=0){sleep+=10;return;}
                CanReceive=Math.min(CanReceive,getMana());
                CanReceive=Math.min(500,CanReceive);
                addMana(CanReceive);
                manaReceiver.receiveMana(CanReceive);
            }
        }
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        cmp.putInt(TAG_X, pos.getX());
        cmp.putInt(TAG_Y, pos.getY());
        cmp.putInt(TAG_Z, pos.getZ());
        super.writeToPacketNBT(cmp);
    }

    @Override
    public int getMaxMana() {
        return 15000;
    }

    @Override
    public int getColor() {
        return 0x00FFFF;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return RadiusDescriptor.Rectangle.square(getEffectivePos(), 6);
    }
}
