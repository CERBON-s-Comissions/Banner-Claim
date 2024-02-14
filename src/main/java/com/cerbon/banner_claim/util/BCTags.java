package com.cerbon.banner_claim.util;

import com.cerbon.banner_claim.BannerClaim;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BCTags {
    public static final TagKey<Block> BANNER_PROTECTION = BlockTags.create(new ResourceLocation(BannerClaim.MOD_ID, "banner_protection"));
}
