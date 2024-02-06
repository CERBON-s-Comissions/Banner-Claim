package com.cerbon.banner_claim.capability;

import com.cerbon.banner_claim.capability.custom.ChunkBlockCache;
import com.cerbon.banner_claim.capability.custom.ChunkBlockCacheProvider;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class BCCapabilities {

    public static Optional<ChunkBlockCache> getChunkBlockCache(Level level) {
        return level.getCapability(ChunkBlockCacheProvider.CHUNK_BLOCK_CACHE).resolve();
    }
}
