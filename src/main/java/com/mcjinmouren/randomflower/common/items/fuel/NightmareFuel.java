package com.mcjinmouren.randomflower.common.items.fuel;

import net.minecraft.world.item.Item;

/**
 * This class is currently not in use.
 * 这个类目前没有什么用。
 */

public class NightmareFuel extends Item {

    private final int burnTime = 2000;

    public NightmareFuel(Properties p_41383_) {
        super(p_41383_);
    }
/*
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return burnTime;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, LivingEntity living) {
        super.finishUsingItem(stack, level, living);

        living.hurt(level.damageSources().magic(), 6);
        ItemStack item = new ItemStack(RandomFlowerItems.SPIRIT_FUEL.get());

        if (living instanceof Player player)
            if (!player.getInventory().add(item))
                player.drop(item, false);
        return stack;
    }*/
}