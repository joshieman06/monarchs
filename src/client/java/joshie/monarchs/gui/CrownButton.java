package joshie.monarchs.gui;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.impl.client.NarrationMessages;
import io.github.cottonmc.cotton.gui.impl.client.WidgetTextures;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import io.github.cottonmc.cotton.gui.widget.icon.Icon;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.cottonmc.cotton.gui.impl.LibGuiCommon.id;

public class CrownButton extends WWidget {
    private static final int ICON_SPACING = 2;
    public static final ButtonTextures IRON_BUTTON = new ButtonTextures(
            Identifier.of("monarchs", "monarch_iron"),
            id("widget/button_disabled_dark"),
            Identifier.of("monarchs", "monarch_iron_active")
    );
    public static final ButtonTextures FLAME_BUTTON = new ButtonTextures(
            Identifier.of("monarchs", "monarch_flame"),
            id("widget/button_disabled_dark"),
            Identifier.of("monarchs", "monarch_flame_active")
    );
    public static final ButtonTextures FURY_BUTTON = new ButtonTextures(
            Identifier.of("monarchs", "monarch_rage"),
            id("widget/button_disabled_dark"),
            Identifier.of("monarchs", "monarch_rage_active")
    );

    @Nullable private Text label;
    protected int color = WLabel.DEFAULT_TEXT_COLOR;
    /**
     * The size (width/height) of this button's icon in pixels.
     * @since 6.4.0
     */
    protected List<Text> tooltip;
    protected int iconSize = 16;
    private boolean enabled = true;
    protected HorizontalAlignment alignment = HorizontalAlignment.CENTER;

    @Nullable private Runnable onClick;
    @Nullable private Icon icon = null;
    @Nullable private ButtonTextures texture = null;

    /**
     * Constructs a button with no label and no icon.
     */
    public CrownButton(ButtonTextures textures) {
        this.texture = textures;
    }

    /**
     * Constructs a button with an icon.
     *
     * @param icon the icon
     * @since 2.2.0
     */
    public CrownButton(@Nullable Icon icon) {
        this.icon = icon;
    }

    /**
     * Constructs a button with a label.
     *
     * @param label the label
     */
    public CrownButton(@Nullable Text label) {
        this.label = label;
    }

    public void setTooltip(List<Text> tooltip) {
        this.tooltip = tooltip;
    }

    @Override
    public void addTooltip(TooltipBuilder tooltip) {
        for (int i = 0; i < this.tooltip.size(); i++) {
            tooltip.add(this.tooltip.get(i));
        }
    }

    /**
     * Constructs a button with an icon and a label.
     *
     * @param icon  the icon
     * @param label the label
     * @since 2.2.0
     */
    public CrownButton(@Nullable Icon icon, @Nullable Text label) {
        this.icon = icon;
        this.label = label;
    }

    @Override
    public boolean canResize() {
        return true;
    }

    @Override
    public boolean canFocus() {
        return true;
    }



    @Environment(EnvType.CLIENT)
    @Override
    public void paint(DrawContext context, int x, int y, int mouseX, int mouseY) {
        boolean hovered = isWithinBounds(mouseX, mouseY);
        ButtonTextures textures = texture;
        context.drawGuiTexture(textures.get(enabled, hovered || isFocused()), x, y, getWidth(), getHeight());

        if (icon != null) {
            icon.paint(context, x+ICON_SPACING, y+(getHeight()-iconSize)/2, iconSize);
        }

        if (label!=null) {
            int color = 0xE0E0E0;
            if (!enabled) {
                color = 0xA0A0A0;
            } /*else if (hovered) {
				color = 0xFFFFA0;
			}*/

            int xOffset = (icon != null && alignment == HorizontalAlignment.LEFT) ? ICON_SPACING+iconSize+ICON_SPACING : 0;
            ScreenDrawing.drawStringWithShadow(context, label.asOrderedText(), alignment, x + xOffset, y + ((getHeight() - 8) / 2), width, color); //LibGuiClient.config.darkMode ? darkmodeColor : color);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);

        if (enabled && isWithinBounds(x, y)) {
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));

            if (onClick!=null) onClick.run();
            return InputResult.PROCESSED;
        }

        return InputResult.IGNORED;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public InputResult onKeyPressed(int ch, int key, int modifiers) {
        if (isActivationKey(ch)) {
            onClick(0, 0, 0);
            return InputResult.PROCESSED;
        }

        return InputResult.IGNORED;
    }

    /**
     * Gets the click handler of this button.
     *
     * @return the click handler
     * @since 2.2.0
     */
    @Nullable
    public Runnable getOnClick() {
        return onClick;
    }

    /**
     * Sets the click handler of this button.
     *
     * @param onClick the new click handler
     * @return this button
     */
    public CrownButton setOnClick(@Nullable Runnable onClick) {
        this.onClick = onClick;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public CrownButton setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public @Nullable Text getLabel() {
        return label;
    }

    public CrownButton setLabel(Text label) {
        this.label = label;
        return this;
    }

    public HorizontalAlignment getAlignment() {
        return alignment;
    }

    public CrownButton setAlignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    /**
     * Gets the current height / width of the icon.
     *
     * @return the current height / width of the icon
     * @since 6.4.0
     */
    public int getIconSize() {
        return iconSize;
    }

    /**
     * Sets the new size of the icon.
     *
     * @param iconSize the new height and width of the icon
     * @return this button
     * @since 6.4.0
     */
    public CrownButton setIconSize(int iconSize) {
        this.iconSize = iconSize;
        return this;
    }

    /**
     * Gets the icon of this button.
     *
     * @return the icon
     * @since 2.2.0
     */
    @Nullable
    public Icon getIcon() {
        return icon;
    }

    /**
     * Sets the icon of this button.
     *
     * @param icon the new icon
     * @return this button
     * @since 2.2.0
     */
    public CrownButton setIcon(@Nullable Icon icon) {
        this.icon = icon;
        return this;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void addNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, ClickableWidget.getNarrationMessage(getLabel()));

        if (isEnabled()) {
            if (isFocused()) {
                builder.put(NarrationPart.USAGE, NarrationMessages.Vanilla.BUTTON_USAGE_FOCUSED);
            } else if (isHovered()) {
                builder.put(NarrationPart.USAGE, NarrationMessages.Vanilla.BUTTON_USAGE_HOVERED);
            }
        }
    }
}
