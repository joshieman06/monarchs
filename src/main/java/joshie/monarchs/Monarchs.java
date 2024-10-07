package joshie.monarchs;

import joshie.monarchs.access.PlayerEntityAccess;
import joshie.monarchs.armor.MonarchsArmorMaterials;
import joshie.monarchs.faction.UUIDStorage;
import joshie.monarchs.item.CrownItem;
import joshie.monarchs.item.ModItemGroups;
import joshie.monarchs.network.RulerGuiPacket;
import joshie.monarchs.network.RulerPacket;
import net.fabricmc.api.ModInitializer;


import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import static joshie.monarchs.item.ModItems.registerModItems;
import static joshie.monarchs.nbt.FactionClass.FACTION;
import static joshie.monarchs.nbt.InvitedClass.INVITED;
import static joshie.monarchs.nbt.OwnerClass.OWNER;
import static joshie.monarchs.nbt.RulerTypeClass.RULER_TYPE;
import static joshie.monarchs.statuseffects.MonarchEffects.*;
import net.minecraft.core.Registry;

public class Monarchs implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("monarchs");
	public static final String MOD_ID = "monarchs";
    public static Holder<MobEffect> IRON_EFFECT;
	public static Holder<MobEffect> FLAME_EFFECT;
	public static Holder<MobEffect> FURY_EFFECT;
	public static UUIDStorage factionStorage = new UUIDStorage();

    @Override
	public void onInitialize() {
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(this::onPlayerKill);
		PayloadTypeRegistry.playS2C().register(RulerGuiPacket.PACKET_ID, RulerGuiPacket.PACKET_CODEC);
		PayloadTypeRegistry.playC2S().register(RulerPacket.PACKET_ID, RulerPacket.PACKET_CODEC);
		RulerPacket.registerServerReceiver();
		IRON_EFFECT = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath("monarchs", "iron_will"), IRON);
		FLAME_EFFECT = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath("monarchs", "blazing_heart"), FLAME);
		FURY_EFFECT = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath("monarchs", "rage"), FURY);

		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath("monarchs", "faction"), FACTION);
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath("monarchs", "invited"), INVITED);
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath("monarchs", "ruler_type"), RULER_TYPE);
		Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, ResourceLocation.fromNamespaceAndPath("monarchs", "owner"), OWNER);
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		MonarchsArmorMaterials.initialize();
		ModItemGroups.registerItemGroups();
		registerModItems();

		ServerLifecycleEvents.SERVER_STARTED.register(server -> factionStorage.loadUUIDs(server));
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> factionStorage.saveUUIDs(server));
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			UUIDStorage.checkFaction(handler.player);
		});
	}

	private void onPlayerKill(ServerLevel serverWorld, Entity entity, LivingEntity livingEntity) {
		// Check if the attacker is a player
		if (entity instanceof ServerPlayer attacker && livingEntity instanceof Player victim) {
			if (((PlayerEntityAccess)attacker).monarchs$getFaction() != null) {
				if (((PlayerEntityAccess)victim).monarchs$getFaction() != null) {
					grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "valor"));
				}
				if (true) { // Get attached ruler != null
					grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "honor"));
				}
			}
			if (attacker.getInventory().getArmor(3).getItem() instanceof CrownItem) {

				switch(attacker.getInventory().getArmor(3).get(RULER_TYPE)) {
					case "iron":
						grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "iron_monarch_kill"));
						switch() { //victim.getAttached(Ruler)
							case "iron":
								grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "iron_kill_iron"));
								break;
							case "flame":
								grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "iron_kill_flame"));
								break;
							case "fury":
								grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "iron_kill_fury"));
								break;
						}
						break;
					case "flame":
						grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "flame_monarch_kill"));
						switch() { //victim.getAttached(Ruler)
							case "iron":
								grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "flame_kill_iron"));
								break;
							case "flame":
								grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "flame_kill_flame"));
								break;
							case "fury":
								grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "flame_kill_fury"));
								break;
						}
						break;
					case "fury":
						grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "fury_monarch_kill"));
						switch() { //victim.getAttached(Ruler)
							case "iron":
								grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "fury_kill_iron"));
								break;
							case "flame":
								grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "fury_kill_flame"));
								break;
							case "fury":
								grantAdvancement(attacker, ResourceLocation.fromNamespaceAndPath("monarchs", "fury_kill_fury"));
								break;
						}
						break;
				}
				// Attach ruler = null
			}
		}
	}

	public static void grantAdvancement(ServerPlayer player, ResourceLocation advancementId) {
		AdvancementHolder advancement = player.server.getAdvancements().get(advancementId);
		AdvancementProgress advancementProgress = player.getAdvancements().getOrStartProgress(advancement);
		if (!advancementProgress.isDone()) {
            for (String string : advancementProgress.getRemainingCriteria()) {
                player.getAdvancements().award(advancement, string);
            }
		}
	}
}