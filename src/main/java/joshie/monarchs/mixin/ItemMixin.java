package joshie.monarchs.mixin;

import joshie.monarchs.access.PlayerEntityAccess;
import joshie.monarchs.item.CrownItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static joshie.monarchs.item.ModItems.FACTION_INVITE;
import static joshie.monarchs.nbt.FactionClass.FACTION;
import static joshie.monarchs.nbt.InvitedClass.INVITED;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    public void onSwordRightClick(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (stack.getItem() instanceof SwordItem) {
            if (user.getInventory().getArmorStack(3).getItem() instanceof CrownItem) {
                ItemStack inviteItem = (new ItemStack(FACTION_INVITE));
                inviteItem.set(FACTION, user.getInventory().getArmorStack(3).get(FACTION));
                inviteItem.set(INVITED, entity.getName().toString());
                LoreComponent stackLore = inviteItem.getOrDefault(DataComponentTypes.LORE, LoreComponent.DEFAULT).with(Text.of("§6  " + user.getName().getString() + "'s Faction")).with(Text.of("§7  Issued to " + entity.getName().getString() + ""));
                inviteItem.set(DataComponentTypes.LORE, stackLore);
                if (user instanceof PlayerEntity && entity instanceof PlayerEntity) {
                    if (user.getOffHandStack().isEmpty() && user.getInventory().contains(new ItemStack(Items.PAPER)) && !(((PlayerEntity) entity).getInventory().contains(inviteItem))) {
                        if (((PlayerEntityAccess) entity).monarchs$getFaction() == null) {
                            for (int i = 0; i < user.getInventory().size(); i++) {
                                ItemStack istack = user.getInventory().getStack(i);
                                if (istack.getItem() == Items.PAPER) {
                                    istack.decrement(1);
                                    break;
                                }
                            }
                            ((PlayerEntity) entity).giveItemStack(inviteItem);
                            cir.setReturnValue(ActionResult.SUCCESS);
                        }
                    }
                }
            }
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}

