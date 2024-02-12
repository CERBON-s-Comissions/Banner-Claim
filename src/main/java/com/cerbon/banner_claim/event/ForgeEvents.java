package com.cerbon.banner_claim.event;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.cerbon.banner_claim.capability.custom.ChunkBlockCacheProvider;
import com.cerbon.banner_claim.util.BCUtils;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BannerClaim.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesLevel(AttachCapabilitiesEvent<Level> event) {
        if (event.getObject() == null || event.getObject().getCapability(ChunkBlockCacheProvider.CHUNK_BLOCK_CACHE).isPresent()) return;
        event.addCapability(new ResourceLocation(BannerClaim.MOD_ID, "chunk_block_cache_capability"), new ChunkBlockCacheProvider());
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide) return;

        if (event.getEntity() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative() && !serverPlayer.isSpectator()) {
            BCUtils.ifBannerClaimContainsChunkDo(new ChunkPos(event.getPos()), (ServerLevel) serverPlayer.level(), (bannerClaimPos, bannerClaimBlockEntity) -> {
                BannerTier tier = bannerClaimBlockEntity.getBannerTier();
                int bannerTierRange = BannerClaimBlockEntity.getBannerTierRange(tier);

                boolean isWithinBannerRange = Math.abs(bannerClaimPos.getX() - event.getPos().getX()) <= bannerTierRange && Math.abs(bannerClaimPos.getY() - 10) <= event.getPos().getY() && Math.abs(bannerClaimPos.getZ() - event.getPos().getZ()) <= bannerTierRange;
                boolean isOwner = serverPlayer == bannerClaimBlockEntity.getOwner();

                if (isWithinBannerRange && !isOwner)
                    event.setUseBlock(Event.Result.DENY);
            });
        }
    }

    @SubscribeEvent
    public static void onPlaceBlock(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel().isClientSide() || event.getEntity() == null) return;

        if (event.getEntity() instanceof ServerPlayer serverPlayer && !serverPlayer.isCreative() && !serverPlayer.isSpectator()) {
            BCUtils.ifBannerClaimContainsChunkDo(new ChunkPos(event.getPos()), (ServerLevel) serverPlayer.level(), (bannerClaimPos, bannerClaimBlockEntity) -> {
                BannerTier tier = bannerClaimBlockEntity.getBannerTier();
                int bannerTierRange = BannerClaimBlockEntity.getBannerTierRange(tier);

                boolean isWithinBannerRange = Math.abs(bannerClaimPos.getX() - event.getPos().getX()) <= bannerTierRange && Math.abs(bannerClaimPos.getY() - 10) <= event.getPos().getY() && Math.abs(bannerClaimPos.getZ() - event.getPos().getZ()) <= bannerTierRange;
                boolean isOwner = serverPlayer == bannerClaimBlockEntity.getOwner();

                if (isWithinBannerRange && !isOwner)
                    event.setCanceled(true);
            });
        }
    }

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        if (event.getLevel().isClientSide) return;

        BCUtils.ifBannerClaimContainsChunkDo(new ChunkPos(BlockPos.containing(event.getExplosion().getPosition())), (ServerLevel) event.getLevel(), (bannerClaimPos, bannerClaimBlockEntity) -> {
            BannerTier tier = bannerClaimBlockEntity.getBannerTier();
            int bannerTierRange  = BannerClaimBlockEntity.getBannerTierRange(tier);

            event.getAffectedBlocks().removeIf(pos -> Math.abs(bannerClaimPos.getX() - pos.getX()) <= bannerTierRange && Math.abs(bannerClaimPos.getY() - 10) <= pos.getY() && Math.abs(bannerClaimPos.getZ() - pos.getZ()) <= bannerTierRange);
        });
    }

    @SubscribeEvent
    public static void onMobGriefing(EntityMobGriefingEvent event) {
        if (event.getEntity().level().isClientSide) return;

        BCUtils.ifBannerClaimContainsChunkDo(new ChunkPos(event.getEntity().blockPosition()), (ServerLevel) event.getEntity().level(), (bannerClaimPos, bannerClaimBlockEntity) -> {
            BannerTier tier = bannerClaimBlockEntity.getBannerTier();
            int bannerTierRange  = BannerClaimBlockEntity.getBannerTierRange(tier);

            if (Math.abs(bannerClaimPos.getX() - event.getEntity().getX()) <= bannerTierRange && Math.abs(bannerClaimPos.getY() - 10) <= event.getEntity().getY() && Math.abs(bannerClaimPos.getZ() - event.getEntity().getZ()) <= bannerTierRange)
                event.setResult(Event.Result.DENY);
        });
    }

    @SubscribeEvent
    public static void customCommands(RegisterCommandsEvent event){
        var claimCommands = Commands.literal("bannerclaim");

        claimCommands.then(
                Commands.literal("group")
                        .then(Commands.literal("add").then(Commands.argument("players", GameProfileArgument.gameProfile()).suggests(BCUtils::suggestPlayers).executes(BCUtils::addToGroup)))
                        .then(Commands.literal("remove").then(Commands.argument("players", GameProfileArgument.gameProfile()).suggests(BCUtils::suggestPlayersInGroup).executes(BCUtils::removeFromGroup)))
                        .then(Commands.literal("players").executes(BCUtils::showAllPlayers))
        );

        event.getDispatcher().register(claimCommands);
    }
}
