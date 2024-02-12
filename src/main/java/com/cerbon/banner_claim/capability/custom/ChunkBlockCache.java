package com.cerbon.banner_claim.capability.custom;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class ChunkBlockCache {
    private final Map<ChunkPos, HashMap<Block, HashSet<BlockPos>>> map = new Object2ObjectOpenHashMap<>();

    public void addToChunk(ChunkPos chunkPos, Block block, BlockPos pos) {
        HashMap<Block, HashSet<BlockPos>> chunk = map.getOrDefault(chunkPos, new HashMap<>());
        HashSet<BlockPos> blocks = chunk.getOrDefault(block, new HashSet<>());
        blocks.add(pos);
        chunk.put(block, blocks);
        map.put(chunkPos, chunk);
    }

    public List<BlockPos> getBlocksFromChunk(ChunkPos chunkPos, Block... blocks) {
        if (!map.containsKey(chunkPos)) return null;

        List<BlockPos> positions = new ArrayList<>();

        for (Block block : blocks)
            if (map.get(chunkPos).containsKey(block))
                positions.addAll(map.get(chunkPos).get(block).stream().toList());

        return positions;
    }

    public void removeFromChunk(ChunkPos chunkPos, Block block, BlockPos pos) {
        if (map.containsKey(chunkPos) && map.get(chunkPos).containsKey(block)) {
            map.get(chunkPos).get(block).remove(pos);

            if (map.get(chunkPos).get(block).isEmpty())
                map.get(chunkPos).remove(block);

            if (map.get(chunkPos).isEmpty())
                map.remove(chunkPos);
        }
    }
}
