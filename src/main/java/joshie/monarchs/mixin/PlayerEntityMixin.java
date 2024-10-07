package joshie.monarchs.mixin;

import joshie.monarchs.Monarchs;
import joshie.monarchs.access.PlayerEntityAccess;
import joshie.monarchs.attachment.MonarchsAttachmentTypes;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.Unique;


import java.util.List;
import java.util.UUID;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityAccess {

    @Unique
    public String monarch_type;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public UUID monarchs$getFaction() {

    }

    @Override
    public void monarchs$setFaction(UUID faction) {

    }


}
