//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package joshie.monarchs.item;

import joshie.monarchs.Monarchs;
import joshie.monarchs.access.PlayerEntityAccess;
import joshie.monarchs.network.RulerGuiPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import static joshie.monarchs.Monarchs.*;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.*;

import static joshie.monarchs.nbt.FactionClass.FACTION;
import static joshie.monarchs.nbt.OwnerClass.OWNER;
import static joshie.monarchs.nbt.RulerTypeClass.RULER_TYPE;


public class CrownItem extends ArmorItem {

    public CrownItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }



    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.get(FACTION) == null && ((PlayerEntityAccess)entity).monarchs$getFaction() != null) {
            stack.decrement(1);
        }
        if (slot == 3 && entity instanceof PlayerEntity player1 && player1.getInventory().getArmorStack(3) == stack) {
            if (stack.get(OWNER) == null) {
                    stack.set(OWNER, entity.getName().toString());
                    stack.addEnchantment(entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(Identifier.ofVanilla("binding_curse")).get(), 1);
            } else {
                if (Objects.equals(stack.get(FACTION), ((PlayerEntityAccess) entity).monarchs$getFaction())) {
                    ((PlayerEntityAccess) entity).monarchs$setFaction(null);
                    stack.set(OWNER, entity.getName().toString());
                    stack.addEnchantment(entity.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(Identifier.ofVanilla("binding_curse")).get(), 1);
                    // Heir to the throne advancement
                    if (entity instanceof ServerPlayerEntity) {
                        grantAdvancement((ServerPlayerEntity) entity, Identifier.of("monarchs", "a_worthy_successor"));
                    }
                    ((PlayerEntity)entity).sendMessage(Text.literal("Inherited a Monarchy").setStyle(Style.EMPTY.withColor(Formatting.GOLD)), true);
                }
            }
            UUID faction = stack.get(FACTION);
            if (faction == null) {
                UUID new_faction = UUID.randomUUID();
                stack.set(FACTION, new_faction);
                stack.set(OWNER, entity.getName().toString());
                if (entity instanceof ServerPlayerEntity) {
                    ServerPlayNetworking.send((ServerPlayerEntity)entity, new RulerGuiPacket("helmet"));
                    grantAdvancement((ServerPlayerEntity) entity, Identifier.of("monarchs", "monarchy"));
                }
                ((PlayerEntity)entity).sendMessage(Text.literal("Founded a Monarchy").setStyle(Style.EMPTY.withColor(Formatting.BLUE)), true);
            } else {
                for (PlayerEntity player : world.getPlayers()) {
                    if (player.getUuid() != entity.getUuid()) {
                        if (Objects.equals(((PlayerEntityAccess) player).monarchs$getFaction(), faction)) {
                            if (player.distanceTo(entity) <= 64) {
                                switch (stack.get(RULER_TYPE)) {
                                    case "iron":
                                        player.addStatusEffect(new StatusEffectInstance(Monarchs.IRON_EFFECT, 61, 0));
                                        break;
                                    case "flame":
                                        player.addStatusEffect(new StatusEffectInstance(Monarchs.FLAME_EFFECT, 61, 0));
                                        break;
                                    case "fury":
                                        player.addStatusEffect(new StatusEffectInstance(Monarchs.FURY_EFFECT, 61, 0));
                                        break;
                                    case null, default:
                                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0));
                                }

                            }
                        }
                    }
                }
            }
        } else if (!Objects.equals(stack.get(OWNER), entity.getName().toString()) && !Objects.equals(stack.get(FACTION), ((PlayerEntityAccess)entity).monarchs$getFaction())) {
            // Overthrown advancement
            if (entity instanceof ServerPlayerEntity) {
                grantAdvancement((ServerPlayerEntity) entity, Identifier.of("monarchs", "overthrown"));
            }
            // Overthrow logic
            ((PlayerEntity)entity).sendMessage(Text.literal("Overthrown a Monarchy").setStyle(Style.EMPTY.withColor(Formatting.RED)), true);
            factionStorage.addUUID(stack.get(FACTION));
            stack.decrement(1);
        }
    }
    @Override
    public int getProtection() {
        return 0;
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof  ServerPlayerEntity) {
            ServerPlayNetworking.send((ServerPlayerEntity) user, new RulerGuiPacket("Open the gui please!"));
            return TypedActionResult.success(user.getStackInHand(hand), false);
        }
        else {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
    }
}