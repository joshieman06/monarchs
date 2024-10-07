package joshie.monarchs.nbt;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;


public class RulerTypeClass {
    public static final DataComponentType<String> RULER_TYPE = DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.stringUtf8(64)).build();
}