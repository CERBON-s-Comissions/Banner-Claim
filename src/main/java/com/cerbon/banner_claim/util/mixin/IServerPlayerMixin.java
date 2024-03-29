package com.cerbon.banner_claim.util.mixin;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public interface IServerPlayerMixin {

    HashSet<UUID> bc_getPlayersInGroup();
    void bc_addPlayerToGroup(UUID player);

    void bc_removePlayerFromGroup(UUID player);

    List<String> bc_getPlayersInGroupName();

    int bc_getCooldown();

    void bc_setCooldown(int bc_Cooldown);
}
