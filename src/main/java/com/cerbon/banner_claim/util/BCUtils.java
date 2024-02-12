package com.cerbon.banner_claim.util;

import com.cerbon.banner_claim.block.BCBlocks;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.cerbon.banner_claim.capability.BCCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.BiConsumer;

public class BCUtils {

    public static void ifBannerClaimContainsChunkDo(ChunkPos chunkPos, ServerLevel serverLevel, BiConsumer<BlockPos, BannerClaimBlockEntity> biConsumer) {
        BCCapabilities.getChunkBlockCache(serverLevel).ifPresent(chunkCache -> {
            for (int x = chunkPos.x - 8; x <= chunkPos.x + 8; x++)
                for (int z = chunkPos.z - 8; z <= chunkPos.z + 8; z++) {
                    List<BlockPos> blocks = chunkCache.getBlocksFromChunk(new ChunkPos(x, z), BCBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toArray(Block[]::new));
                    if (blocks == null) continue;

                    for (BlockPos blockPos : blocks) {
                        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);

                        if (blockEntity instanceof BannerClaimBlockEntity bannerClaim) {
                            biConsumer.accept(blockPos, bannerClaim);
                            break;
                        }
                    }
                }
        });
    }
}
