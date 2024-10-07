package joshie.monarchs.statuseffects;

import joshie.monarchs.statuseffects.effects.Iron;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MonarchEffects {

    public static final MobEffect IRON = new Iron(0xaab3bf).addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.iron_will_slowness"), -0.10, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.iron_will_armor"), 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.iron_will_knockback"), 0.50, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.iron_will_fall"), +1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final MobEffect FLAME = new Iron(0xff5e00).addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.blazing_heart_speed"), 0.20, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.blazing_heart_armor"), -0.20, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.blazing_heart_knockback"), -0.20, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.JUMP_STRENGTH, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.blazing_heart_jump"), 0.1, AttributeModifier.Operation.ADD_VALUE);
    public static final MobEffect FURY = new Iron(0xb52700).addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.rage_attack"), 0.20, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.rage_attack_speed"), 0.20, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.FALL_DAMAGE_MULTIPLIER, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.rage_fall_damage"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath("monarchs", "effect.rage_health"), -6.0, AttributeModifier.Operation.ADD_VALUE);

}
