package com.cerbon.banner_claim.util;

import com.cerbon.banner_claim.block.BCBlocks;
import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.cerbon.banner_claim.capability.BCCapabilities;
import com.cerbon.banner_claim.util.mixin.IServerPlayerMixin;
import com.cerbon.cerbons_api.api.static_utilities.RegistryUtils;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class BCUtils {

    public static void ifBannerClaimContainsChunkDo(ChunkPos chunkPos, ServerLevel serverLevel, BiConsumer<BlockPos, BannerClaimBlockEntity> biConsumer) {
        BCCapabilities.getChunkBlockCache(serverLevel).ifPresent(chunkCache -> {
            int rangeToCheck = Math.round((float) Arrays.stream(BannerTier.values())
                    .mapToInt(BannerClaimBlockEntity::getBannerTierRange)
                    .max()
                    .orElse(0) / 8.0f);

            for (int x = chunkPos.x - rangeToCheck; x <= chunkPos.x + rangeToCheck; x++)
                for (int z = chunkPos.z - rangeToCheck; z <= chunkPos.z + rangeToCheck; z++) {
                    List<BlockPos> blocks = chunkCache.getBlocksFromChunk(new ChunkPos(x, z), BCBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new));
                    if (blocks == null) continue;

                    for (BlockPos blockPos : blocks) {
                        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);

                        if (blockEntity instanceof BannerClaimBlockEntity bannerClaim && bannerClaim.timeToActivate <= 0) {
                            biConsumer.accept(blockPos, bannerClaim);
                            break;
                        }
                    }
                }
        });
    }

    public static int addToGroup(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        IServerPlayerMixin playerMixin = (IServerPlayerMixin) player;

        for (GameProfile prof : GameProfileArgument.getGameProfiles(context, "players")) {
            if (!Arrays.stream(context.getSource().getServer().getPlayerList().getPlayerNamesArray()).toList().contains(prof.getName())) {
                player.displayClientMessage(Component.translatable("warn.banner_claim.cant_add.invalid", prof.getName()).withStyle(ChatFormatting.RED), false);
                return 0;
            }

            if (playerMixin.bc_getPlayersInGroup().contains(prof.getId())) {
                player.displayClientMessage(Component.translatable( "command.banner_claim.cant_add", prof.getName()).withStyle(ChatFormatting.RED), false);
                return 0;
            }

            playerMixin.bc_addPlayerToGroup(prof.getId());
            player.displayClientMessage(Component.translatable("command.banner_claim.add_successful", prof.getName()).withStyle(ChatFormatting.GREEN), false);
            return 1;
        }

        return 0;
    }

    public static int removeFromGroup(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        IServerPlayerMixin playerMixin = (IServerPlayerMixin) player;

        for (GameProfile prof : GameProfileArgument.getGameProfiles(context, "players")) {
            if (!playerMixin.bc_getPlayersInGroup().contains(prof.getId())) {
                player.displayClientMessage(Component.translatable( "command.banner_claim.cant_remove", prof.getName()).withStyle(ChatFormatting.RED), false);
                return 0;
            }

            playerMixin.bc_removePlayerFromGroup(prof.getId());
            playerMixin.bc_getPlayersInGroupName().remove(prof.getName());
            player.displayClientMessage(Component.translatable("command.banner_claim.remove_successfully", prof.getName()).withStyle(ChatFormatting.GREEN), false);
            return 1;
        }

        return 0;
    }

    public static int showAllPlayers(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        IServerPlayerMixin playerMixin = (IServerPlayerMixin) player;

        if (playerMixin.bc_getPlayersInGroup().isEmpty()) {
            player.displayClientMessage(Component.translatable("command.banner_claim.players.empty").withStyle(ChatFormatting.RED), false);
            return 0;
        }

        for(String name : playerMixin.bc_getPlayersInGroupName())
            player.displayClientMessage(Component.literal(name).withStyle(ChatFormatting.GOLD), false);

        return 1;
    }

    public static int showProtections(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();

        ForgeRegistries.BLOCKS.getEntries().stream().filter(block -> block.getValue().defaultBlockState().is(BCTags.BANNER_PROTECTION)).forEach(block -> player.displayClientMessage(Component.literal(RegistryUtils.getItemKeyAsString(block.getValue().asItem())).withStyle(ChatFormatting.GOLD), false));

        return 1;
    }

    public static CompletableFuture<Suggestions> suggestPlayersInGroup(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        IServerPlayerMixin playerMixin = (IServerPlayerMixin) player;

        for(String name : playerMixin.bc_getPlayersInGroupName())
            builder.suggest(name);

        return builder.buildFuture();
    }

    public static CompletableFuture<Suggestions> suggestPlayers(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(context.getSource().getServer().getPlayerList().getPlayerNamesArray(), builder);
    }
}
