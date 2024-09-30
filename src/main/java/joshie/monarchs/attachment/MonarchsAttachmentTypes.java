package joshie.monarchs.attachment;

import com.mojang.serialization.Codec;
import joshie.monarchs.Monarchs;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.UUID;

public class MonarchsAttachmentTypes {
    public static final AttachmentType<UUID> PERSISTENT_FACTION = AttachmentRegistry.<UUID>builder() // Builder for finer control
            .persistent(Uuids.CODEC) // persistent
            .copyOnDeath() // will persist over entity death and respawn
            .initializer(() -> null) // default value
            .buildAndRegister(Identifier.of("monarchs.faction"));

    public static final AttachmentType<String> RULER = AttachmentRegistry.<String>builder() // Builder for finer control
            .persistent(Codec.STRING) // persistent
            .copyOnDeath()
            .initializer(() -> null) // default value
            .buildAndRegister(Identifier.of("monarchs.faction"));
}