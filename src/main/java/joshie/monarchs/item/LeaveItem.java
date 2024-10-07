//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package joshie.monarchs.item;

import joshie.monarchs.access.PlayerEntityAccess;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class LeaveItem extends Item {
    public LeaveItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (((PlayerEntityAccess)user).monarchs$getFaction() != null) {
            ((PlayerEntityAccess) user).monarchs$setFaction(null);
            itemStack.shrink(1);
            (user).displayClientMessage(Component.literal("Left a Monarchy").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), true);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

}