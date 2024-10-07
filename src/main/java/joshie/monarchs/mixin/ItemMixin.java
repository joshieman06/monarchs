package joshie.monarchs.mixin;

import joshie.monarchs.access.PlayerEntityAccess;
import joshie.monarchs.item.CrownItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemLore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static joshie.monarchs.item.ModItems.FACTION_INVITE;
import static joshie.monarchs.nbt.FactionClass.FACTION;
import static joshie.monarchs.nbt.InvitedClass.INVITED;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "interactLivingEntity", at = @At("HEAD"), cancellable = true)
    public void onSwordRightClick(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (stack.getItem() instanceof SwordItem) {
            if (user.getInventory().getArmor(3).getItem() instanceof CrownItem) {
                ItemStack inviteItem = (new ItemStack(FACTION_INVITE));
                inviteItem.set(FACTION, user.getInventory().getArmor(3).get(FACTION));
                inviteItem.set(INVITED, entity.getName().toString());
                ItemLore stackLore = inviteItem.getOrDefault(DataComponents.LORE, ItemLore.EMPTY).withLineAdded(Component.literal("§6  " + user.getName().getString() + "'s Faction")).withLineAdded(Component.literal("§7  Issued to " + entity.getName().getString()));
                inviteItem.set(DataComponents.LORE, stackLore);
                if (user instanceof Player && entity instanceof Player) {
                    if (user.getOffhandItem().isEmpty() && user.getInventory().contains(new ItemStack(Items.PAPER)) && !(((Player) entity).getInventory().contains(inviteItem))) {
                        if (((PlayerEntityAccess) entity).monarchs$getFaction() == null) {
                            for (int i = 0; i < user.getInventory().getContainerSize(); i++) {
                                ItemStack istack = user.getInventory().getItem(i);
                                if (istack.getItem() == Items.PAPER) {
                                    istack.shrink(1);
                                    break;
                                }
                            }
                            ((Player) entity).addItem(inviteItem);
                            cir.setReturnValue(InteractionResult.SUCCESS);
                        }
                    }
                }
            }
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}

