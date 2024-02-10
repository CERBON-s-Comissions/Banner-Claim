package com.cerbon.banner_claim.capability.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;

import java.util.List;

public interface IChunkBlockCache {
    void addToChunk(ChunkPos chunkPos, Block block, BlockPos pos);
    List<BlockPos> getBlocksFromChunk(ChunkPos chunkPos, Block... blocks);
    void removeFromChunk(ChunkPos chunkPos, Block block, BlockPos pos);
}
