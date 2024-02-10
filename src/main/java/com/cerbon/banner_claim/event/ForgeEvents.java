package com.cerbon.banner_claim.event;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.cerbon.banner_claim.capability.custom.ChunkBlockCacheProvider;
import com.cerbon.banner_claim.util.mixin.IEntityMixin;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BannerClaim.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesLevel(AttachCapabilitiesEvent<Level> event) {
        if (event.getObject() == null || event.getObject().getCapability(ChunkBlockCacheProvider.CHUNK_BLOCK_CACHE).isPresent()) return;
        event.addCapability(new ResourceLocation(BannerClaim.MOD_ID, "chunk_block_cache_capability"), new ChunkBlockCacheProvider());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT) return;

        ServerPlayer player = (ServerPlayer) event.player;
        IEntityMixin playerMixin = ((IEntityMixin) player);
        BannerClaimBlockEntity bannerClaimBlockEntity = playerMixin.getBannerClaimBlockEntity();

        if (bannerClaimBlockEntity == null) return;

        boolean hasPlayer = bannerClaimBlockEntity.playersInBox.contains(player);
        boolean isOwner = player == bannerClaimBlockEntity.getOwner();

        if (hasPlayer && !isOwner) {
            player.getAbilities().mayBuild = false;
            player.connection.send(new ClientboundPlayerAbilitiesPacket(player.getAbilities()));

        } else if (!player.getAbilities().mayBuild && playerMixin.getBannerClaimBlockEntity() != null) {
            event.player.getAbilities().mayBuild = true;
            player.connection.send(new ClientboundPlayerAbilitiesPacket(player.getAbilities()));
            playerMixin.setBannerClaimBlockEntity(null);
        }
    }

//    @SubscribeEvent
//    public static void onExplosion(ExplosionEvent.Start event) {
//        if (!((IEntityMixin)event.getExplosion().getDamageSource().getDirectEntity()).canDoGriefing())
//            event.setCanceled(true);
//    }
//
//    @SubscribeEvent
//    public static void onMobGriefing(EntityMobGriefingEvent event) {
//        if (!((IEntityMixin)event.getEntity()).canDoGriefing())
//            event.setResult(Event.Result.DENY);
//    }
}
