//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package joshie.monarchs.item;

import joshie.monarchs.access.PlayerEntityAccess;


import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;

import static joshie.monarchs.Monarchs.grantAdvancement;
import static joshie.monarchs.nbt.FactionClass.FACTION;
import static joshie.monarchs.nbt.InvitedClass.INVITED;


public class InviteItem extends Item {
    public InviteItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (user.getInventory().getArmor(3).getItem() instanceof CrownItem) {
            return InteractionResultHolder.fail(itemStack);
        }
        UUID faction = itemStack.get(FACTION);
        if (faction != null && Objects.equals(itemStack.get(INVITED), user.getName().toString())) {
            ((PlayerEntityAccess) user).monarchs$setFaction(faction);
            itemStack.shrink(1);
            (user).displayClientMessage(Component.literal("Joined a Monarchy").setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE)), true);
            if (user instanceof ServerPlayer) {
                grantAdvancement((ServerPlayer) user, ResourceLocation.fromNamespaceAndPath("monarchs", "knighthood"));
            }
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

}