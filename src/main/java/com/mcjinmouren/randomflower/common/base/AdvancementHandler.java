package com.mcjinmouren.randomflower.common.base;

import com.mcjinmouren.randomflower.RandomFlower;
import com.mcjinmouren.randomflower.api.advancement.IAdvancementRequirement;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import vazkii.botania.common.helper.PlayerHelper;

import java.util.Map;
import java.util.Objects;

/**
 * This class is used to handle various in - game events for players.
 * 该类用来处理玩家游戏中的不同事件。
 */

@Mod.EventBusSubscriber
public final class AdvancementHandler {

    public static final AdvancementHandler INSTANCE = new AdvancementHandler();

    public void grantAdvancement(ServerPlayer player, String id)
    {
        PlayerHelper.grantCriterion(player, new ResourceLocation(RandomFlower.MOD_ID, "main/" + id), "code_triggered");
    }

    /**
     * This method is used to check achievements.
     * 该方法用来检查成就。
     */
    public static boolean checkAdvancement(Player player, String modid, String advancement)
    {
        ResourceLocation id = ResourceLocation.tryParse(modid + ":main/" + advancement);
        if (id != null)
        {
            if (player instanceof ServerPlayer)
            {
                ServerAdvancementManager manager = Objects.requireNonNull(player.getServer()).getAdvancements();
                PlayerAdvancements advancements = ((ServerPlayer) player).getAdvancements();
                Advancement adv = manager.getAdvancement(id);
                if (adv != null)
                {
                    return !advancements.getOrStartProgress(adv).isDone();
                }
            }
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public static Advancement getSideAdvancement(String modid, String advancement)
    {
        ResourceLocation id = ResourceLocation.tryParse(modid + ":main/" + advancement);
        if (id != null)
        {
            ClientPacketListener netHandler = null;
            if (Minecraft.getInstance().player != null) {
                netHandler = Minecraft.getInstance().player.connection;
            }
            ClientAdvancements manager = null;
            if (netHandler != null) {
                manager = netHandler.getAdvancements();
            }
            if (manager != null) {
                return manager.getAdvancements().get(id);
            }
        }
        return null;
    }

    public static boolean hasDone(String modid, String advancement)
    {
        ResourceLocation id = ResourceLocation.tryParse(modid + ":main/" + advancement);
        if (id != null)
        {
            var conn = Minecraft.getInstance().getConnection();
            if (conn != null)
            {
                var cm = conn.getAdvancements();
                Advancement adv = cm.getAdvancements().get(id);
                if (adv != null)
                {
                    Map<Advancement, AdvancementProgress> progressMap = RandomFlowerReflectHelper.getPrivateValue(ClientAdvancements.class, cm, "f_104390_");
                    AdvancementProgress progress = progressMap != null ? progressMap.get(adv) : null;
                    return progress != null && progress.isDone();
                }
            }
        }
        return false;
    }

    /**
     * This method is triggered when the player uses an item.
     * 如果玩家使用物品，会触发这个方法。
     */
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event.isCancelable() && !event.getEntity().isCreative())
        {
            if (event.getItemStack().getItem() instanceof IAdvancementRequirement r)
            {
                if (checkAdvancement(event.getEntity(), RandomFlower.MOD_ID, r.getAdvancementName()))
                    event.setCanceled(true);
            }
        }
    }

    /**
     * This method is used to get tooltip information.
     * 该方法用来获取提示信息。
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onTooltip(ItemTooltipEvent event)
    {
        if (event.getItemStack().getItem() instanceof IAdvancementRequirement r)
        {
            var playerSP = Minecraft.getInstance().player;
            /*if (playerSP != null && !hasDone(Utils.MOD_ID, r.getAdvancementName()))
            {
                event.getToolTip().add(1, new TextComponent(" "));
                event.getToolTip().add(1, new TranslatableComponent("randomflowermisc.description", new TranslatableComponent("advancement.randomflower:" + r.getAdvancementName() + ".title").withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.RED));
            }*/
        }
    }

    /**
     * This method is triggered when there is an entity update.
     * 当有实体更新时，会触发这个方法。
     */
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent event)
    {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide)
        {
            if (player.isCreative())
            {
                return;
            }

            CuriosApi.getCuriosHelper().getEquippedCurios(player).ifPresent((c) ->
            {
                for (int i = 0; i < c.getSlots(); i++)
                {
                    final ItemStack stack = c.getStackInSlot(i);
                    if (stack.getItem() instanceof IAdvancementRequirement r)
                    {
                        if (checkAdvancement(player, RandomFlower.MOD_ID, r.getAdvancementName()))
                        {
                            c.setStackInSlot(i, ItemStack.EMPTY);
                            player.drop(stack, false);
                        }
                    }
                }
            });

            for (final EquipmentSlot slot : EquipmentSlot.values())
            {
                final ItemStack stack = player.getItemBySlot(slot);
                if (stack.getItem() instanceof IAdvancementRequirement r)
                {
                    if (checkAdvancement(player, RandomFlower.MOD_ID, r.getAdvancementName()))
                    {
                        player.setItemSlot(slot, ItemStack.EMPTY);
                        player.drop(stack, false);
                    }
                }
            }
        }
    }
}
