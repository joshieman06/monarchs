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
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementManager;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import static joshie.monarchs.attachment.MonarchsAttachmentTypes.RULER;
import static joshie.monarchs.item.ModItems.registerModItems;
import static joshie.monarchs.nbt.FactionClass.FACTION;
import static joshie.monarchs.nbt.InvitedClass.INVITED;
import static joshie.monarchs.nbt.OwnerClass.OWNER;
import static joshie.monarchs.nbt.RulerTypeClass.RULER_TYPE;
import static joshie.monarchs.statuseffects.MonarchEffects.*;

public class Monarchs implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("monarchs");
	public static final String MOD_ID = "monarchs";
    public static RegistryEntry<StatusEffect> IRON_EFFECT;
	public static RegistryEntry<StatusEffect> FLAME_EFFECT;
	public static RegistryEntry<StatusEffect> FURY_EFFECT;
	public static UUIDStorage factionStorage = new UUIDStorage();

    @Override
	public void onInitialize() {
		ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(this::onPlayerKill);
		PayloadTypeRegistry.playS2C().register(RulerGuiPacket.PACKET_ID, RulerGuiPacket.PACKET_CODEC);
		PayloadTypeRegistry.playC2S().register(RulerPacket.PACKET_ID, RulerPacket.PACKET_CODEC);
		RulerPacket.registerServerReceiver();
		IRON_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("monarchs", "iron_will"), IRON);
		FLAME_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("monarchs", "blazing_heart"), FLAME);
		FURY_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of("monarchs", "rage"), FURY);

		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of("monarchs", "faction"), FACTION);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of("monarchs", "invited"), INVITED);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of("monarchs", "ruler_type"), RULER_TYPE);
		Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of("monarchs", "owner"), OWNER);
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

	private void onPlayerKill(ServerWorld serverWorld, Entity entity, LivingEntity livingEntity) {
		// Check if the attacker is a player
		if (entity instanceof ServerPlayerEntity attacker && livingEntity instanceof PlayerEntity victim) {
			if (((PlayerEntityAccess)attacker).monarchs$getFaction() != null) {
				if (((PlayerEntityAccess)victim).monarchs$getFaction() != null) {
					grantAdvancement(attacker, Identifier.of("monarchs", "valor"));
				}
				if (victim.getAttached(RULER) != null) {
					grantAdvancement(attacker, Identifier.of("monarchs", "honor"));
				}
			}
			if (attacker.getInventory().getArmorStack(3).getItem() instanceof CrownItem) {

				switch(attacker.getInventory().getArmorStack(3).get(RULER_TYPE)) {
					case "iron":
						grantAdvancement(attacker, Identifier.of("monarchs", "iron_monarch_kill"));
						switch(victim.getAttached(RULER)) {
							case "iron":
								grantAdvancement(attacker, Identifier.of("monarchs", "iron_kill_iron"));
								break;
							case "flame":
								grantAdvancement(attacker, Identifier.of("monarchs", "iron_kill_flame"));
								break;
							case "fury":
								grantAdvancement(attacker, Identifier.of("monarchs", "iron_kill_fury"));
								break;
						}
						break;
					case "flame":
						grantAdvancement(attacker, Identifier.of("monarchs", "flame_monarch_kill"));
						switch(victim.getAttached(RULER)) {
							case "iron":
								grantAdvancement(attacker, Identifier.of("monarchs", "flame_kill_iron"));
								break;
							case "flame":
								grantAdvancement(attacker, Identifier.of("monarchs", "flame_kill_flame"));
								break;
							case "fury":
								grantAdvancement(attacker, Identifier.of("monarchs", "flame_kill_fury"));
								break;
						}
						break;
					case "fury":
						grantAdvancement(attacker, Identifier.of("monarchs", "fury_monarch_kill"));
						switch(victim.getAttached(RULER)) {
							case "iron":
								grantAdvancement(attacker, Identifier.of("monarchs", "fury_kill_iron"));
								break;
							case "flame":
								grantAdvancement(attacker, Identifier.of("monarchs", "fury_kill_flame"));
								break;
							case "fury":
								grantAdvancement(attacker, Identifier.of("monarchs", "fury_kill_fury"));
								break;
						}
						break;
				}
				victim.setAttached(RULER, null);
			}
		}
	}

	public static void grantAdvancement(ServerPlayerEntity player, Identifier advancementId) {
		AdvancementEntry advancement = player.server.getAdvancementLoader().get(advancementId);
		AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
		if (!advancementProgress.isDone()) {
			Iterator var4 = advancementProgress.getUnobtainedCriteria().iterator();
			while (var4.hasNext()) {
				String string = (String) var4.next();
				player.getAdvancementTracker().grantCriterion(advancement, string);
			}
		}
	}
}