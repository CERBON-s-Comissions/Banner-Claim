package com.cerbon.banner_claim.event;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.block.BCBlocks;
import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.cerbon.banner_claim.capability.BCCapabilities;
import com.cerbon.banner_claim.capability.custom.ChunkBlockCacheProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

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

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        if (event.getLevel().isClientSide) return;

        BCCapabilities.getChunkBlockCache(event.getLevel()).ifPresent(capability -> {
            ChunkPos chunkPos = new ChunkPos(BlockPos.containing(event.getExplosion().getPosition()));

            for (int x = chunkPos.x - 8; x <= chunkPos.x + 8; x++)
                for (int z = chunkPos.z - 8; z <= chunkPos.z + 8; z++) {
                    List<BlockPos> blocks = capability.getBlocksFromChunk(new ChunkPos(x, z), BCBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new));

                    for (BlockPos blockPos : blocks) {
                        BlockEntity blockEntity = event.getLevel().getBlockEntity(blockPos);

                        if (blockEntity instanceof BannerClaimBlockEntity bannerClaimBlockEntity) {
                            BannerTier tier = bannerClaimBlockEntity.getBannerTier();
                            int bannerTierRange  = BannerClaimBlockEntity.getBannerTierRange(tier);

                            event.getAffectedBlocks().removeIf(pos -> Math.abs(blockPos.getX() - pos.getX()) <= bannerTierRange && Math.abs(blockPos.getY() - pos.getY()) <= event.getLevel().getHeight() && Math.abs(blockPos.getZ() - pos.getZ()) <= bannerTierRange);
                        }
                    }
                }
        });
    }
//
//    @SubscribeEvent
//    public static void onMobGriefing(EntityMobGriefingEvent event) {
//        if (!((IEntityMixin)event.getEntity()).canDoGriefing())
//            event.setResult(Event.Result.DENY);
//    }
}
