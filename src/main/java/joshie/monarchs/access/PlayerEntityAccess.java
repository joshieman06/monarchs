package joshie.monarchs.access;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

public interface PlayerEntityAccess {

    UUID monarchs$getFaction();

    void monarchs$setFaction(UUID faction);

}
