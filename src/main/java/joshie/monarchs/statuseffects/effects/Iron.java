package joshie.monarchs.statuseffects.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class Iron extends StatusEffect {
    public Iron(int color) {
        super(
                StatusEffectCategory.BENEFICIAL,
                color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {

        return true;
    }


//    @Override
//    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
//
//    }
}