/*
*
*   This flower can now work without a multi-block structure;
*   perhaps a multi-block structure may be added in the future?
*
*/
package com.mcjinmouren.extrabotany.common.blocks.flower.functional;

import com.mcjinmouren.extrabotany.api.core.BlockPosDim;
import com.mcjinmouren.extrabotany.common.blocks.ExtraBotanyFlowerBlocks;
import com.mcjinmouren.extrabotany.common.items.advanced.PositionReader;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;

public class ManalinkuimBlockEntity extends FunctionalFlowerBlockEntity {

    private static final String TAG_X = "x";
    private static final String TAG_Y = "y";
    private static final String TAG_Z = "z";

    private int x;
    private int y;
    private int z;
    private boolean getPos = false;
/*
    private boolean playerclicker = false;

    int cd = 0;
    boolean quartz = true;
    boolean lapis = true;
    boolean obsidion = true;
    boolean multiblock = false;
*/
    @SubscribeEvent
    public static void onPlayerUseItem(PlayerInteractEvent.RightClickBlock event){
        Player player = event.getEntity();
        ItemStack item = player.getMainHandItem();
        BlockPos clickPos = event.getHitVec().getBlockPos();
        BlockEntity blockEntity = event.getLevel().getBlockEntity(clickPos);
        if (item.getItem() instanceof PositionReader && blockEntity instanceof ManalinkuimBlockEntity && player.isShiftKeyDown()){
            BlockPos pos = BlockPosDim.getItemStackBlockPos(item);
            if (pos != null) {
                ((ManalinkuimBlockEntity) blockEntity).x = pos.getX();
                ((ManalinkuimBlockEntity) blockEntity).y = pos.getY();
                ((ManalinkuimBlockEntity) blockEntity).z = pos.getZ();
                ((ManalinkuimBlockEntity) blockEntity).getPos = true;
                //((ManalinkuimBlockEntity) blockEntity).playerclicker = true;
            }
        }
    }

    public ManalinkuimBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyFlowerBlocks.MANALINKUIM, pos, state);
    }

    public void tickFlower() {
        super.tickFlower();
        /*if (cd > 0) cd--;
        if (cd == 0 || playerclicker) {
            List<BlockPos> QUARTZ_LOCATIONS = List.of(new BlockPos(2, -1, 2), new BlockPos(2, -1, 1),
                    new BlockPos(2, -1, 0), new BlockPos(2, -1, -1), new BlockPos(2, -1, -2), new BlockPos(1, -1, 2),
                    new BlockPos(1, -1, -2), new BlockPos(0, -1, 2), new BlockPos(0, -1, -2), new BlockPos(-1, -1, 2),
                    new BlockPos(-1, -1, -2), new BlockPos(-2, -1, 2), new BlockPos(-2, -1, 1), new BlockPos(-2, -1, 0),
                    new BlockPos(-2, -1, -1), new BlockPos(-2, -1, -2));

            List<BlockPos> LAMP_LOCATIONS = List.of(new BlockPos(1, -1, 1), new BlockPos(-1, -1, 1),
                    new BlockPos(1, -1, -1), new BlockPos(-1, -1, -1));

            List<BlockPos> PILLAR_LOCATIONS = List.of(new BlockPos(1, -1, 0), new BlockPos(0, -1, 1),
                    new BlockPos(-1, -1, 0), new BlockPos(0, -1, -1));

            for (BlockPos quartzPos : QUARTZ_LOCATIONS) {
                if (level != null) {
                    if (!isBlock(level, quartzPos, Blocks.QUARTZ_BLOCK)) quartz = false;
                    cd = 6000;
                    playerclicker = false;
                }
            }

            for (BlockPos lampPos : LAMP_LOCATIONS) {
                if (level != null) {
                    if (!isBlock(level, lampPos, Blocks.LAPIS_BLOCK)) lapis = false;
                    cd = 6000;
                    playerclicker = false;
                }
            }

            for (BlockPos pillarPos : PILLAR_LOCATIONS) {
                if (level != null) {
                    if (!isBlock(level, pillarPos, Blocks.OBSIDIAN)) obsidion = false;
                    cd = 6000;
                    playerclicker = false;
                }
            }

            if (quartz && lapis && obsidion) multiblock = true;
        }*/
        //if (multiblock) {
            BlockPos pos = new BlockPos(x, y, z);
            BlockEntity blockEntity = null;
            if (level != null && level.isLoaded(pos)) {
                blockEntity = level.getBlockEntity(pos);
            }
            if (blockEntity instanceof ManaPoolBlockEntity manaReceiver) {
                int receiverMaxMana = manaReceiver.getMaxMana();
                int receiverCurrentMana = manaReceiver.getCurrentMana();
                int CanReceive = receiverMaxMana - receiverCurrentMana;
                if (!manaReceiver.isFull() && getPos) {
                    if (getMana() > 500 && CanReceive > 500) {
                        addMana(-500);
                        manaReceiver.receiveMana(500);
                    } else if (getMana() <= 500 && CanReceive >= getMana()) {
                        addMana(-getMana());
                        manaReceiver.receiveMana(getMana());
                    } else if (CanReceive < getMana()) {
                        addMana(-CanReceive);
                        manaReceiver.receiveMana(CanReceive);
                    }
                }
            //}
        }
    }
    private static boolean isBlock(Level world, BlockPos pos, Block expectedBlock) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.is(expectedBlock);
    }

    @Override
    public void writeToPacketNBT(CompoundTag cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt(TAG_X, x);
        cmp.putInt(TAG_Y, y);
        cmp.putInt(TAG_Z, z);
    }

    @Override
    public void readFromPacketNBT(CompoundTag cmp) {
        super.readFromPacketNBT(cmp);
        x = cmp.getInt(TAG_X);
        y = cmp.getInt(TAG_Y);
        z = cmp.getInt(TAG_Z);
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
