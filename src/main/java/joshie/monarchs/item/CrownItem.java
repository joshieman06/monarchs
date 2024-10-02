//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package joshie.monarchs.item;

import joshie.monarchs.Monarchs;
import joshie.monarchs.access.PlayerEntityAccess;
import joshie.monarchs.network.RulerGuiPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import static joshie.monarchs.Monarchs.*;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;

import static joshie.monarchs.nbt.FactionClass.FACTION;
import static joshie.monarchs.nbt.OwnerClass.OWNER;
import static joshie.monarchs.nbt.RulerTypeClass.RULER_TYPE;


public class CrownItem extends ArmorItem {

    public CrownItem(Holder<ArmorMaterial> material, Type type, Properties settings) {
        super(material, type, settings);
    }



    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (stack.get(FACTION) == null && ((PlayerEntityAccess)entity).monarchs$getFaction() != null) {
            stack.shrink(1);
        }
        if (slot == 3 && entity instanceof Player player1 && player1.getInventory().getArmor(3) == stack) {
            if (stack.get(OWNER) == null) {
                    stack.set(OWNER, entity.getName().toString());
                    stack.enchant(entity.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolder(ResourceLocation.withDefaultNamespace("binding_curse")).get(), 1);
            } else {
                if (Objects.equals(stack.get(FACTION), ((PlayerEntityAccess) entity).monarchs$getFaction())) {
                    ((PlayerEntityAccess) entity).monarchs$setFaction(null);
                    stack.set(OWNER, entity.getName().toString());
                    stack.enchant(entity.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolder(ResourceLocation.withDefaultNamespace("binding_curse")).get(), 1);
                    // Heir to the throne advancement
                    if (entity instanceof ServerPlayer) {
                        grantAdvancement((ServerPlayer) entity, ResourceLocation.fromNamespaceAndPath("monarchs", "a_worthy_successor"));
                    }
                    ((Player)entity).displayClientMessage(Component.literal("Inherited a Monarchy").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), true);
                }
            }
            UUID faction = stack.get(FACTION);
            if (faction == null) {
                UUID new_faction = UUID.randomUUID();
                stack.set(FACTION, new_faction);
                stack.set(OWNER, entity.getName().toString());
                if (entity instanceof ServerPlayer) {
                    ServerPlayNetworking.send((ServerPlayer)entity, new RulerGuiPacket("helmet"));
                    grantAdvancement((ServerPlayer) entity, ResourceLocation.fromNamespaceAndPath("monarchs", "monarchy"));
                }
                ((Player)entity).displayClientMessage(Component.literal("Founded a Monarchy").setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE)), true);
            } else {
                for (Player player : world.players()) {
                    if (player.getUUID() != entity.getUUID()) {
                        if (Objects.equals(((PlayerEntityAccess) player).monarchs$getFaction(), faction)) {
                            if (player.distanceTo(entity) <= 64) {
                                switch (stack.get(RULER_TYPE)) {
                                    case "iron":
                                        player.addEffect(new MobEffectInstance(Monarchs.IRON_EFFECT, 61, 0));
                                        break;
                                    case "flame":
                                        player.addEffect(new MobEffectInstance(Monarchs.FLAME_EFFECT, 61, 0));
                                        break;
                                    case "fury":
                                        player.addEffect(new MobEffectInstance(Monarchs.FURY_EFFECT, 61, 0));
                                        break;
                                    case null, default:
                                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 0));
                                }

                            }
                        }
                    }
                }
            }
        } else if (!Objects.equals(stack.get(OWNER), entity.getName().toString()) && !Objects.equals(stack.get(FACTION), ((PlayerEntityAccess)entity).monarchs$getFaction())) {
            // Overthrown advancement
            if (entity instanceof ServerPlayer) {
                grantAdvancement((ServerPlayer) entity, ResourceLocation.fromNamespaceAndPath("monarchs", "overthrown"));
            }
            // Overthrow logic
            ((Player)entity).displayClientMessage(Component.literal("Overthrown a Monarchy").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), true);
            factionStorage.addUUID(stack.get(FACTION));
            stack.shrink(1);
        }
    }
    @Override
    public int getDefense() {
        return 0;
    }



    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (user instanceof  ServerPlayer) {
            ServerPlayNetworking.send((ServerPlayer) user, new RulerGuiPacket("Open the gui please!"));
            return InteractionResultHolder.success(user.getItemInHand(hand));
        }
        else {
            return InteractionResultHolder.fail(user.getItemInHand(hand));
        }
    }
}