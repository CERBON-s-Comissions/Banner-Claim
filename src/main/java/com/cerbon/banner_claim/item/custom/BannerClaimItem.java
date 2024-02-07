package com.cerbon.banner_claim.item.custom;

import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.block.BannerClaimBlock;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.level.block.Block;

public class BannerClaimItem extends BannerItem {

    public BannerClaimItem(Block block, Block wallBlock, Properties properties) {
        super(block, wallBlock, properties);
    }

    public BannerTier getTier() {
        return ((BannerClaimBlock) this.getBlock()).getTier();
    }
}
