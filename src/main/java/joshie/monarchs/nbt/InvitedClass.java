package joshie.monarchs.nbt;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;

public class InvitedClass {
    public static final ComponentType<String> INVITED = ComponentType.<String>builder().codec(Codec.STRING).packetCodec(PacketCodecs.STRING ).build();
}
