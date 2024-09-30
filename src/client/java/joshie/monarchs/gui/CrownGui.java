package joshie.monarchs.gui;

import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import joshie.monarchs.network.RulerPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class CrownGui extends LightweightGuiDescription {
    public CrownGui() {
        WCardPanel root = new WCardPanel();
        setRootPanel(root);
        root.setSize(256, 170);

        WPlainPanel pageOne = new WPlainPanel();

        WLabel label = new WLabel(Text.of("Become a Monarch of..."));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        pageOne.add(label, 0, 15, 256, 15);

        CrownButton button = new CrownButton(CrownButton.IRON_BUTTON);
        button.setTooltip(List.of(Text.literal("Iron").setStyle(Style.EMPTY.withColor(Formatting.GRAY)), Text.literal(" +10% Armor Toughness").setStyle(Style.EMPTY.withColor(Formatting.BLUE)), Text.literal(" +50% Knockback Resistance").setStyle(Style.EMPTY.withColor(Formatting.BLUE)), Text.literal(" +100% Safe Fall Distance").setStyle(Style.EMPTY.withColor(Formatting.BLUE)), Text.literal(" -10% Movement Speed").setStyle(Style.EMPTY.withColor(Formatting.RED))));
        button.addTooltip(new TooltipBuilder());
        button.setOnClick(() -> {
            // This code runs on the client when you click the button.
            ClientPlayNetworking.send(new RulerPacket("iron"));
            MinecraftClient.getInstance().setScreen(null);
        });
        pageOne.add(button, 6, 36, 81, 128);

        CrownButton button2 = new CrownButton(CrownButton.FLAME_BUTTON);
        button2.setTooltip(List.of(Text.literal("Flame").setStyle(Style.EMPTY.withColor(Formatting.GOLD)), Text.literal(" +20% Movement Speed").setStyle(Style.EMPTY.withColor(Formatting.BLUE)), Text.literal(" +0.5 Jump Height").setStyle(Style.EMPTY.withColor(Formatting.BLUE)), Text.literal(" -20% Armor Toughness").setStyle(Style.EMPTY.withColor(Formatting.RED)), Text.literal(" -20% Knockback Resistance").setStyle(Style.EMPTY.withColor(Formatting.RED))));
        button2.addTooltip(new TooltipBuilder());
        button2.setOnClick(() -> {
            // This code runs on the client when you click the button.
            ClientPlayNetworking.send(new RulerPacket("flame"));
            MinecraftClient.getInstance().setScreen(null);
        });
        pageOne.add(button2, 87, 36, 82, 128);

        CrownButton button3 = new CrownButton(CrownButton.FURY_BUTTON);
        button3.setTooltip(List.of(Text.literal("Fury").setStyle(Style.EMPTY.withColor(Formatting.DARK_RED)), Text.literal(" +20% Attack Damage").setStyle(Style.EMPTY.withColor(Formatting.BLUE)), Text.literal(" +20% Attack Speed").setStyle(Style.EMPTY.withColor(Formatting.BLUE)), Text.literal(" +50% Fall Damage").setStyle(Style.EMPTY.withColor(Formatting.RED)), Text.literal(" -6 Maximum Health").setStyle(Style.EMPTY.withColor(Formatting.RED))));
        button3.addTooltip(new TooltipBuilder());
        button3.setOnClick(() -> {
            // This code runs on the client when you click the button.
            ClientPlayNetworking.send(new RulerPacket("fury"));
            MinecraftClient.getInstance().setScreen(null);

        });
        pageOne.add(button3, 169, 36, 81, 128);

        root.add(pageOne);

        root.validate(this);
    }

}