package joshie.monarchs.network;

import joshie.monarchs.item.CrownItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import static joshie.monarchs.attachment.MonarchsAttachmentTypes.RULER;
import static joshie.monarchs.nbt.RulerTypeClass.RULER_TYPE;


public class RulerPacket implements CustomPayload {

    public static final CustomPayload.Id<RulerPacket> PACKET_ID = new CustomPayload.Id<>(Identifier.of("monarchs", "change_ruler_type"));
    public static final PacketCodec<RegistryByteBuf, RulerPacket> PACKET_CODEC = PacketCodec.of(RulerPacket::encode, RulerPacket::new).cast();

    public RulerPacket(String part) {
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

    public RulerPacket(RegistryByteBuf buf) {
        this.part = buf.readString();
    }

    public void encode(RegistryByteBuf buf) {
        buf.writeString(part);
    }

    public static void registerServerReceiver() {
        ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, (payload, context) -> {
            context.player().getServer().execute(() -> {
                // Handle the packet data on the server side
                String part = payload.getPart();
                // Your handling logic here
                PlayerEntity ruler = (PlayerEntity) context.player().getServerWorld().getEntity(context.player().getUuid());
                assert ruler != null;
                if (ruler.getInventory().getArmorStack(3).getItem() instanceof CrownItem) {
                    ruler.getInventory().getArmorStack(3).set(RULER_TYPE, part);
                } else if (ruler.getMainHandStack().getItem() instanceof CrownItem) {
                    ruler.getMainHandStack().set(RULER_TYPE, part);
                } else if (ruler.getOffHandStack().getItem() instanceof CrownItem) {
                    ruler.getOffHandStack().set(RULER_TYPE, part);
                }
                ruler.setAttached(RULER, part);
                context.player().getWorld().playSound(null, context.player().getBlockPos(), SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.BLOCKS, 1f, 1f);
            });
        });
    }
}
