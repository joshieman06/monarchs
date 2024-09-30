package joshie.monarchs;

import joshie.monarchs.gui.CrownGui;
import joshie.monarchs.gui.CrownScreen;

import joshie.monarchs.item.CrownItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static joshie.monarchs.network.RulerGuiPacket.PACKET_ID;

public class MonarchsClient implements ClientModInitializer {
	private static KeyBinding openGuiKeybind;

	@Override
	public void onInitializeClient() {
		openGuiKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.monarchs.selection", // Translation key
				InputUtil.Type.KEYSYM, // Key type
				GLFW.GLFW_KEY_G,
				"category.monarchs" // Key category
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (openGuiKeybind.wasPressed() && client.player != null) {
				if (client.player.getInventory().getArmorStack(3).getItem() instanceof CrownItem) {
					MinecraftClient.getInstance().setScreen(new CrownScreen(new CrownGui()));
				}
			}
		});

		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		ClientPlayNetworking.registerGlobalReceiver(PACKET_ID, (payload, context) -> {
			context.client().setScreen(new CrownScreen(new CrownGui()));
		});
	}
}