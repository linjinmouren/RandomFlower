package com.mcjinmouren.randomflower.api.core;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

/**
 * This class is used to handle the display returned when a player uses a certain item.
 * 这个类用来处理玩家使用某个物品时返回的显示。
 */

public class PlayerChat {

    public static void sendStatusMessage(Player player, String message) {
        player.displayClientMessage(ilang(message), true);
    }

    public static MutableComponent ilang(String message) {
        return Component.translatable(message);
    }

    public static String lang(String message) {
        return ilang(message).getString();
    }

}
