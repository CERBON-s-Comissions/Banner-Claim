package com.cerbon.banner_claim.client.block.entity.render;

import com.cerbon.banner_claim.block.BCBlocks;
import com.cerbon.banner_claim.block.custom.block.AbstractBannerClaimBlock;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class BannerClaimItemRenderer extends BlockEntityWithoutLevelRenderer {
    public static final BannerClaimItemRenderer INSTANCE = new BannerClaimItemRenderer();

    private final BannerClaimBlockEntity bannerClaim = new BannerClaimBlockEntity(BlockPos.ZERO, BCBlocks.IRON_BANNER.get().defaultBlockState());

    private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    public BannerClaimItemRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
        this.blockEntityRenderDispatcher = blockEntityRenderDispatcher;
    }

    public BannerClaimItemRenderer() {
        this(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext displayContext, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Item item = stack.getItem();

        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();

            BlockEntity blockEntity;
            if (block instanceof AbstractBannerClaimBlock abstractBannerClaimBlock) {
                bannerClaim.fromItem(stack, abstractBannerClaimBlock.getTier());
                blockEntity = bannerClaim;
            } else return;

            this.blockEntityRenderDispatcher.renderItem(blockEntity, poseStack, buffer, packedLight, packedOverlay);
        }
    }
}
