package com.cerbon.banner_claim.mixin.entity;

import com.cerbon.banner_claim.util.mixin.IServerPlayerMixin;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashSet;
import java.util.UUID;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements IServerPlayerMixin {
    @Unique private final HashSet<UUID> bc_playersInGroup = new HashSet<>();

    @Override
    public HashSet<UUID> bc_getPlayersInGroup() {
        return bc_playersInGroup;
    }

    @Override
    public void bc_addPlayerToGroup(UUID player) {
        bc_playersInGroup.add(player);
    }

    @Override
    public void bc_removePlayerFromGroup(UUID player) {
        bc_playersInGroup.remove(player);
    }
}
