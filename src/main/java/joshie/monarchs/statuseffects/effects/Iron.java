package joshie.monarchs.statuseffects.effects;


import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Iron extends MobEffect {
    public Iron(int color) {
        super(
                MobEffectCategory.BENEFICIAL,
                color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }


}