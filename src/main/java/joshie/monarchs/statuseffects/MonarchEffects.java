package joshie.monarchs.statuseffects;

import joshie.monarchs.statuseffects.effects.Iron;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;

public class MonarchEffects {

    public static final StatusEffect IRON = new Iron(0xaab3bf).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, Identifier.of("monarchs", "effect.iron_will_slowness"), -0.10, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, Identifier.of("monarchs", "effect.iron_will_armor"), 0.10, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, Identifier.of("monarchs", "effect.iron_will_knockback"), 0.50, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, Identifier.of("monarchs", "effect.iron_will_fall"), +1.0, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final StatusEffect FLAME = new Iron(0xff5e00).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, Identifier.of("monarchs", "effect.blazing_heart_speed"), 0.20, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, Identifier.of("monarchs", "effect.blazing_heart_armor"), -0.20, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, Identifier.of("monarchs", "effect.blazing_heart_knockback"), -0.20, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_JUMP_STRENGTH, Identifier.of("monarchs", "effect.blazing_heart_jump"), 0.1, EntityAttributeModifier.Operation.ADD_VALUE);
    public static final StatusEffect FURY = new Iron(0xb52700).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, Identifier.of("monarchs", "effect.rage_attack"), 0.20, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, Identifier.of("monarchs", "effect.rage_attack_speed"), 0.20, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER, Identifier.of("monarchs", "effect.rage_fall_damage"), 0.5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, Identifier.of("monarchs", "effect.rage_health"), -6.0, EntityAttributeModifier.Operation.ADD_VALUE);

}
