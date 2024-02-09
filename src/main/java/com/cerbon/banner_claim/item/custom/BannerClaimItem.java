package com.cerbon.banner_claim.item.custom;

import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.block.BannerClaimBlock;
import com.cerbon.banner_claim.client.block.entity.render.BannerClaimItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.NonNullLazy;

import java.util.function.Consumer;

public class BannerClaimItem extends BannerItem {

    public BannerClaimItem(Block block, Block wallBlock, Properties properties) {
        super(block, wallBlock, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy
                    .of(() -> BannerClaimItemRenderer.INSTANCE);

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer.get();
            }
        });
    }

    public BannerTier getTier() {
        return ((BannerClaimBlock) this.getBlock()).getTier();
    }
}
