package joshie.monarchs.network;


import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;


public class RulerGuiPacket implements CustomPayload {

    public static final Id<RulerGuiPacket> PACKET_ID = new Id<>(Identifier.of("monarchs", "open_ruler_gui"));
    public static final PacketCodec<RegistryByteBuf, RulerGuiPacket> PACKET_CODEC = PacketCodec.of(RulerGuiPacket::encode, RulerGuiPacket::new).cast();

    public RulerGuiPacket(String part) {
        this.part = part;
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

    private final String part;


    public String getPart() {
        return part;
    }

    public RulerGuiPacket(RegistryByteBuf buf) {
        this.part = buf.readString();
    }

    public void encode(RegistryByteBuf buf) {
        buf.writeString(part);
    }


}
