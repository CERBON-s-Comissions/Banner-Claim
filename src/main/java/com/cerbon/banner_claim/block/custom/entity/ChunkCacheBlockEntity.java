package com.cerbon.banner_claim.block.custom.entity;

import com.cerbon.banner_claim.capability.BCCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ChunkCacheBlockEntity extends BlockEntity {
    private final Block block;
    private boolean added = false;

    public ChunkCacheBlockEntity(Block block, BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        this.block = block;
    }

    @Override
    public void setRemoved() {
        if (level != null) {
            BCCapabilities.getChunkBlockCache(level).ifPresent(chunkBlockCache -> chunkBlockCache.removeFromChunk(new ChunkPos(worldPosition), block, worldPosition));
            added = false;
        }
        super.setRemoved();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ChunkCacheBlockEntity entity) {
        if (!entity.added) {
            BCCapabilities.getChunkBlockCache(level).ifPresent(chunkBlockCache -> chunkBlockCache.addToChunk(new ChunkPos(pos), entity.block, pos));
            entity.added = true;
        }
    }
}
