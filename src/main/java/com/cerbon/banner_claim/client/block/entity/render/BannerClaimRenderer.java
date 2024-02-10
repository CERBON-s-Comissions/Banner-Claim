package com.cerbon.banner_claim.client.block.entity.render;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.block.BannerClaimBlock;
import com.cerbon.banner_claim.block.custom.block.WallBannerClaimBlock;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BannerClaimRenderer implements BlockEntityRenderer<BannerClaimBlockEntity> {
    public static final String FLAG = "flag";
    private static final String POLE = "pole";
    private static final String BAR = "bar";
    private final ModelPart flag;
    private final ModelPart pole;
    private final ModelPart bar;

    public static final Material IRON_BASE = new Material(Sheets.BANNER_SHEET, new ResourceLocation(BannerClaim.MOD_ID, "entity/iron_banner_base"));
    public static final Material GOLD_BASE = new Material(Sheets.BANNER_SHEET, new ResourceLocation(BannerClaim.MOD_ID, "entity/gold_banner_base"));
    public static final Material EMERALD_BASE = new Material(Sheets.BANNER_SHEET, new ResourceLocation(BannerClaim.MOD_ID, "entity/emerald_banner_base"));
    public static final Material DIAMOND_BASE = new Material(Sheets.BANNER_SHEET, new ResourceLocation(BannerClaim.MOD_ID, "entity/diamond_banner_base"));
    public static final Material NETHERITE_BASE = new Material(Sheets.BANNER_SHEET, new ResourceLocation(BannerClaim.MOD_ID, "entity/netherite_banner_base"));

    public BannerClaimRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelpart = context.bakeLayer(ModelLayers.BANNER);
        this.flag = modelpart.getChild(FLAG);
        this.pole = modelpart.getChild(POLE);
        this.bar = modelpart.getChild(BAR);
    }

    @Override
    public void render(@NotNull BannerClaimBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        List<Pair<Holder<BannerPattern>, DyeColor>> list = blockEntity.getPatterns();
        BannerTier tier = blockEntity.getBannerTier();

        float f = 0.6666667F;
        boolean flag = blockEntity.getLevel() == null;
        poseStack.pushPose();
        long i;
        if (flag) {
            i = 0L;
            poseStack.translate(0.5F, 0.5F, 0.5F);
            this.pole.visible = true;
        } else {
            i = blockEntity.getLevel().getGameTime();
            BlockState blockstate = blockEntity.getBlockState();
            if (blockstate.getBlock() instanceof BannerClaimBlock) {
                poseStack.translate(0.5F, 0.5F, 0.5F);
                float f1 = -RotationSegment.convertToDegrees(blockstate.getValue(BannerClaimBlock.ROTATION));
                poseStack.mulPose(Axis.YP.rotationDegrees(f1));
                this.pole.visible = true;
            } else {
                poseStack.translate(0.5F, -0.16666667F, 0.5F);
                float f3 = -blockstate.getValue(WallBannerClaimBlock.FACING).toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f3));
                poseStack.translate(0.0F, -0.3125F, -0.4375F);
                this.pole.visible = false;
            }
        }

        poseStack.pushPose();
        poseStack.scale(f, -f, -f);
        VertexConsumer vertexconsumer = getBannerTierTexture(tier).buffer(buffer, RenderType::entitySolid);
        this.pole.render(poseStack, vertexconsumer, packedLight, packedOverlay);
        this.bar.render(poseStack, vertexconsumer, packedLight, packedOverlay);
        BlockPos blockpos = blockEntity.getBlockPos();
        float f2 = ((float)Math.floorMod(blockpos.getX() * 7L + blockpos.getY() * 9L + blockpos.getZ() * 13L + i, 100L) + partialTick) / 100.0F;
        this.flag.xRot = (-0.0125F + 0.01F * Mth.cos(((float)Math.PI * 2F) * f2)) * (float)Math.PI;
        this.flag.y = -32.0F;
        renderPatterns(poseStack, buffer, packedLight, packedOverlay, this.flag, getBannerTierTexture(tier), true, list);
        poseStack.popPose();
        poseStack.popPose();
    }

    public static void renderPatterns(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, ModelPart flagPart, Material flagMaterial, boolean banner, List<Pair<Holder<BannerPattern>, DyeColor>> patterns) {
        renderPatterns(poseStack, bufferSource, packedLight, packedOverlay, flagPart, flagMaterial, banner, patterns, false);
    }

    public static void renderPatterns(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, ModelPart flagPart, Material flagMaterial, boolean banner, List<Pair<Holder<BannerPattern>, DyeColor>> patterns, boolean glint) {
        flagPart.render(poseStack, flagMaterial.buffer(bufferSource, RenderType::entitySolid), packedLight, packedOverlay);

        for(int i = 0; i < 17 && i < patterns.size(); ++i) {
            Pair<Holder<BannerPattern>, DyeColor> pair = patterns.get(i);
            float[] afloat = pair.getSecond().getTextureDiffuseColors();

            pair.getFirst().unwrapKey().map(pattern -> banner ? Sheets.getBannerMaterial(pattern) : Sheets.getShieldMaterial(pattern)).ifPresent(
                    material -> flagPart.render(poseStack, material.buffer(bufferSource, RenderType::entityNoOutline), packedLight, packedOverlay, afloat[0], afloat[1], afloat[2], 1.0F));
        }
    }

    public static Material getBannerTierTexture(BannerTier tier) {
        return switch (tier) {
            case IRON -> IRON_BASE;
            case GOLD -> GOLD_BASE;
            case EMERALD -> EMERALD_BASE;
            case DIAMOND -> DIAMOND_BASE;
            case NETHERITE -> NETHERITE_BASE;
        };
    }
}
