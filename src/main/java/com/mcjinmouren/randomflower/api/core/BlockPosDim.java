package com.mcjinmouren.randomflower.api.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class can conveniently handle content related to coordinates,
 * including coordinate transformation and so on. In addition,
 * it can also detect the existence of multi-block structures.
 * 这个类可以方便地处理一些于坐标有关的内容，包括坐标的转化等等。
 * 除此以外，它还可以检测多方块结构的存在。
 */

public class BlockPosDim {

    public static final BlockPosDim ORIGIN = new BlockPosDim(BlockPos.ZERO, "");
    private int x;
    private int y;
    private int z;
    private BlockPos pos;
    private String dimension;
    private String name;
    private Vec3 hitVec = Vec3.ZERO;
    private Direction side;
    private Direction sidePlayerFacing;
    private Level level;

    public BlockPosDim(Entity entity) {
        this(entity.blockPosition(), entity.level());
    }

    public BlockPosDim(BlockPos pos, Level level) {
        this(pos, dimensionToString(level));
        this.level=level;
    }

    public BlockPosDim(BlockPos pos, String dimension) {
        this(pos, dimension, null);
    }

    public BlockPosDim(BlockPos pos, String dimension, CompoundTag stackTag) {
        setX(pos.getX());
        setY(pos.getY());
        setZ(pos.getZ());
        this.setPos(pos);
        this.setDimension(dimension);
        if (stackTag != null && stackTag.contains("display")) {
            //
            CompoundTag displayTag = stackTag.getCompound("display");
            if (displayTag != null && displayTag.contains("Name", 8)) {
                //
                Component namec = Component.Serializer.fromJson(displayTag.getString("Name"));
                this.name = namec.getString();
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension,level, x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BlockPosDim other = (BlockPosDim) obj;
        return Objects.equals(dimension, other.dimension) && Objects.equals(pos, other.pos) && Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x) && Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y) && Double.doubleToLongBits(z) == Double.doubleToLongBits(other.z);
    }

    @Override
    public String toString() {
        return getDisplayString();
    }

    public boolean isOrigin() {
        return this.equals(ORIGIN);
    }

    public BlockPos toBlockPos() {
        return new BlockPos(this.x, this.y, this.z);
    }

    public double getDistance(BlockPos pos) {
        double deltX = this.x - pos.getX();
        double deltY = this.y - pos.getY();
        double deltZ = this.z - pos.getZ();
        return Math.sqrt(deltX * deltX + deltY * deltY + deltZ * deltZ);
    }

    public String getDisplayString() {
        String prefix = "";
        if (name != null && !name.isEmpty()) {
            prefix = name + " ";
        }
        String d = dimension == null ? "" : dimension.replace("minecraft:", "");
        return prefix + d + " (" + (int) getX() + ", " + (int) getY() + ", " + (int) getZ() + ")";
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public ServerLevel getTargetLevel(Level world) {
        if (world == null || world.getServer() == null || dimension == null) {
            return null;
        }
        return world.getServer().getLevel(stringToDimension(getDimension()));
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getY() {
        return y;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Direction getSide() {
        return side == null ? Direction.UP : side;
    }

    public void setSide(Direction side) {
        this.side = side;
    }

    public Vec3 getHitVec() {
        return hitVec;
    }

    public void setHitVec(Vec3 hitVec) {
        this.hitVec = hitVec;
    }

    public Direction getSidePlayerFacing() {
        return sidePlayerFacing;
    }

    public void setSidePlayerFacing(Direction sidePlayerFacing) {
        this.sidePlayerFacing = sidePlayerFacing;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public static String dimensionToString(Level world) {
        return world.dimension().location().toString();
    }

    public static ResourceKey<Level> stringToDimension(String key) {
        return ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(key));
    }

    public static BlockPos getItemStackBlockPos(ItemStack item) {
        if (item.isEmpty() || item.getTag() == null || !item.getTag().contains("xpos")) {
            return null;
        }
        CompoundTag tag = item.getOrCreateTag();
        return getBlockPos(tag);
    }

    public static BlockPos getBlockPos(CompoundTag tag) {
        return new BlockPos(tag.getInt("xpos"), tag.getInt("ypos"), tag.getInt("zpos"));
    }

    public boolean checkStructure(HashMap<BlockPos, Object> structuralDescription) {
        if (structuralDescription.isEmpty())return true;
        for (Map.Entry<BlockPos, Object> entry : structuralDescription.entrySet()) {
            BlockState blockState = this.level.getBlockState(getRelativeBlockPos(entry.getKey()));
            Object value = entry.getValue();
            if (value instanceof Block block) {
                if (!forListBlock(blockState, block)) return false;
            } else if (value instanceof Block[] blockList) {
                if (!forListBlock(blockState, blockList)) return false;
            }else return false;
        }
        return true;
    }

    private BlockPos getRelativeBlockPos(BlockPos relativePos) {
        return new BlockPos(this.x + relativePos.getX(), this.y + relativePos.getY(), this.z + relativePos.getZ());
    }

    private boolean forListBlock(BlockState blockState, Block... list){
        for (Block block1 : list) {
            if (blockState.is(block1))return true;
        }
        return false;
    }

    public static void setItemStackBlockPos(ItemStack item, BlockPos pos) {
        if (pos == null || item.isEmpty()) {
            return;
        }
        setItemStackNBTVal(item, "xpos", pos.getX());
        setItemStackNBTVal(item, "ypos", pos.getY());
        setItemStackNBTVal(item, "zpos", pos.getZ());
    }

    public static void setItemStackNBTVal(ItemStack item, String prop, int value) {
        if (item.isEmpty()) {
            return;
        }
        item.getOrCreateTag().putInt(prop, value);
    }

    public static String blockPosToString(BlockPos pos) {
        return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
    }

}
