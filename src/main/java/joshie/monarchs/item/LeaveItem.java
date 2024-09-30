//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package joshie.monarchs.item;

import joshie.monarchs.access.PlayerEntityAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class LeaveItem extends Item {
    public LeaveItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (((PlayerEntityAccess)user).monarchs$getFaction() != null) {
            ((PlayerEntityAccess) user).monarchs$setFaction(null);
            itemStack.decrement(1);
            (user).sendMessage(Text.literal("Left a Monarchy").setStyle(Style.EMPTY.withColor(Formatting.RED)), true);
            return TypedActionResult.consume(itemStack);
        }
        return TypedActionResult.pass(itemStack);
    }

}