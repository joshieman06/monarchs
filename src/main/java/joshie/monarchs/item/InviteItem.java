//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package joshie.monarchs.item;

import joshie.monarchs.access.PlayerEntityAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;


import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.*;

import static joshie.monarchs.Monarchs.grantAdvancement;
import static joshie.monarchs.nbt.FactionClass.FACTION;
import static joshie.monarchs.nbt.InvitedClass.INVITED;


public class InviteItem extends Item {
    public InviteItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (user.getInventory().getArmorStack(3).getItem() instanceof CrownItem) {
            return TypedActionResult.fail(itemStack);
        }
        UUID faction = itemStack.get(FACTION);
        if (faction != null && Objects.equals(itemStack.get(INVITED), user.getName().toString())) {
            ((PlayerEntityAccess) user).monarchs$setFaction(faction);
            itemStack.decrement(1);
            (user).sendMessage(Text.literal("Joined a Monarchy").setStyle(Style.EMPTY.withColor(Formatting.BLUE)), true);
            if (user instanceof ServerPlayerEntity) {
                grantAdvancement((ServerPlayerEntity) user, Identifier.of("monarchs", "knighthood"));
            }
            return TypedActionResult.consume(itemStack);
        }
        return TypedActionResult.pass(itemStack);
    }

}