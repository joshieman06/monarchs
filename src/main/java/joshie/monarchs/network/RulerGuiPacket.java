package joshie.monarchs.network;

import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;


public class RulerGuiPacket implements CustomPacketPayload {

    // Packet ID and Codec
    public static final ResourceLocation PACKET_ID = ResourceLocation.fromNamespaceAndPath("monarchs", "open_ruler_gui");
    public static final StreamCodec<RulerGuiPacket, FriendlyByteBuf> PACKET_CODEC = StreamCodec.of(StreamEncoder<a, a>, StreamDecoder<>);

    // Packet fields
    private final String part;

    // Constructor to initialize packet data
    public RulerGuiPacket(String part) {
        this.part = part;
    }

    // Getter for the part
    public String getPart() {
        return part;
    }

    // Decode constructor
    public RulerGuiPacket(FriendlyByteBuf buf) {
        this.part = buf.readUtf();
    }

    // Encode method for writing to the packet buffer
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(part);
    }

    // Return the packet type

    // Static decode method for decoding from FriendlyByteBuf
    public static RulerGuiPacket decode(FriendlyByteBuf buf) {
        return new RulerGuiPacket(buf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}
