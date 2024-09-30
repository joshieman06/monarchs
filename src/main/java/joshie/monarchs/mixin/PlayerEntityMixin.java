package joshie.monarchs.mixin;

import joshie.monarchs.Monarchs;
import joshie.monarchs.access.PlayerEntityAccess;
import joshie.monarchs.attachment.MonarchsAttachmentTypes;
import joshie.monarchs.faction.UUIDStorage;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;


import java.util.List;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityAccess {

    @Unique
    public String monarch_type;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public UUID monarchs$getFaction() {
        return this.getAttached(MonarchsAttachmentTypes.PERSISTENT_FACTION);
    }

    @Override
    public void monarchs$setFaction(UUID faction) {
        this.setAttached(MonarchsAttachmentTypes.PERSISTENT_FACTION, faction);
    }


}
