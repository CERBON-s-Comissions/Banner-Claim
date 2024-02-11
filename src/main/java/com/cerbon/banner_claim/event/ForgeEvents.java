package com.cerbon.banner_claim.event;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.capability.custom.ChunkBlockCacheProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BannerClaim.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesLevel(AttachCapabilitiesEvent<Level> event) {
        if (event.getObject() == null || event.getObject().getCapability(ChunkBlockCacheProvider.CHUNK_BLOCK_CACHE).isPresent()) return;
        event.addCapability(new ResourceLocation(BannerClaim.MOD_ID, "chunk_block_cache_capability"), new ChunkBlockCacheProvider());
    }

//    @SubscribeEvent
//    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
//        if (event.side == LogicalSide.CLIENT) return;
//
//        ServerPlayer player = (ServerPlayer) event.player;
//        IEntityMixin playerMixin = ((IEntityMixin) player);
//        BannerClaimBlockEntity bannerClaimBlockEntity = playerMixin.getBannerClaimBlockEntity();
//
//        if (bannerClaimBlockEntity == null || player.isCreative() || player.isSpectator()) return;
//
//        boolean isInBannerRange = bannerClaimBlockEntity.playersInBox.contains(player);
//        boolean isOwner = player == bannerClaimBlockEntity.getOwner();
//
//        if (isInBannerRange && !isOwner) {
//            player.getAbilities().mayBuild = false;
//            player.connection.send(new ClientboundPlayerAbilitiesPacket(player.getAbilities()));
//
//        } else if (!player.getAbilities().mayBuild && playerMixin.getBannerClaimBlockEntity() != null) {
//            event.player.getAbilities().mayBuild = true;
//            player.connection.send(new ClientboundPlayerAbilitiesPacket(player.getAbilities()));
//            playerMixin.setBannerClaimBlockEntity(null);
//        }
//    }

//    @SubscribeEvent
//    public static void onExplosion(ExplosionEvent.Detonate event) {
//        if (event.getLevel().isClientSide) return;
//
//        ChunkPos chunkPos = new ChunkPos(BlockPos.containing(event.getExplosion().getPosition()));
//        BCCapabilities.getChunkBlockCache(event.getLevel()).ifPresent(capability -> {
//            for (int x = chunkPos.x - 4; x <= chunkPos.x + 4; x++)
//                for (int z = chunkPos.z - 4; z <= chunkPos.z + 4; z++) {
//                    List<BlockPos> blocks = capability.getBlocksFromChunk(new ChunkPos(x, z), BCBlocks.EMERALD_BANNER.get());
//
//                    for (BlockPos blockPos : blocks) {
//                        event.getAffectedBlocks().removeIf(pos -> Math.abs(blockPos.getX() - pos.getX()) <= 32 && Math.abs(blockPos.getY() - pos.getY()) <= 32 && Math.abs(blockPos.getZ() - pos.getZ()) <= 32);
//                    }
//                }
//        });
//    }
//
//    @SubscribeEvent
//    public static void onMobGriefing(EntityMobGriefingEvent event) {
//        if (!((IEntityMixin)event.getEntity()).canDoGriefing())
//            event.setResult(Event.Result.DENY);
//    }
}
