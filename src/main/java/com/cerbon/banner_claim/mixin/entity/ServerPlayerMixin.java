package com.cerbon.banner_claim.mixin.entity;

import com.cerbon.banner_claim.util.mixin.IServerPlayerMixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.UUID;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements IServerPlayerMixin {
    @Unique private final HashSet<UUID> bc_playersInGroup = new HashSet<>();

    @Unique private int bc_Cooldown;

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void bc_addCustomData(CompoundTag tag, CallbackInfo ci) {
        if (!bc_playersInGroup.isEmpty()) {
            ListTag uuidList = new ListTag();

            for (UUID uuid : bc_playersInGroup)
                uuidList.add(StringTag.valueOf(uuid.toString()));

            tag.put("OwnerGroup", uuidList);
        }

        tag.putInt("BannerCooldown", bc_Cooldown);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void bc_loadCustomData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("OwnerGroup")) {
            ListTag uuidList = tag.getList("OwnerGroup", 8);

            for (Tag uuid : uuidList)
                bc_addPlayerToGroup(UUID.fromString(uuid.getAsString()));
        }

        bc_Cooldown = tag.getInt("BannerCooldown");
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void bc_tick(CallbackInfo ci) {
        if (bc_Cooldown > 0)
            bc_Cooldown--;
    }

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

    @Override
    public int bc_getCooldown() {
        return bc_Cooldown;
    }

    @Override
    public void bc_setCooldown(int bc_Cooldown) {
        this.bc_Cooldown = bc_Cooldown;
    }
}
