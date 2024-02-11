package com.cerbon.banner_claim.mixin.screen;

import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.cerbon.banner_claim.client.block.entity.render.BannerClaimRenderer;
import com.cerbon.banner_claim.item.custom.BannerClaimItem;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin extends AbstractContainerScreen<LoomMenu> {

    @Shadow private ModelPart flag;

    @Shadow @Nullable private List<Pair<Holder<BannerPattern>, DyeColor>> resultBannerPatterns;

    @Shadow @Final private static ResourceLocation BG_LOCATION;

    @Shadow private float scrollOffs;

    @Shadow private boolean displayPatterns;

    @Shadow private boolean hasMaxPatterns;

    @Shadow private int startRow;

    @Shadow protected abstract void renderPattern(GuiGraphics pGuiGraphics, Holder<BannerPattern> pPatern, int pX, int pY);

    @Shadow private ItemStack bannerStack;

    @Shadow private ItemStack dyeStack;

    @Shadow private ItemStack patternStack;

    @Shadow protected abstract int totalRowCount();

    public LoomScreenMixin(LoomMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Inject(method = "renderBg", at = @At(value = "HEAD"), cancellable = true)
    private void bc_renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        this.renderBackground(guiGraphics);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(BG_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        Slot slot = this.menu.getBannerSlot();
        Slot slot1 = this.menu.getDyeSlot();
        Slot slot2 = this.menu.getPatternSlot();
        Slot slot3 = this.menu.getResultSlot();
        if (!slot.hasItem()) {
            guiGraphics.blit(BG_LOCATION, i + slot.x, j + slot.y, this.imageWidth, 0, 16, 16);
        }

        if (!slot1.hasItem()) {
            guiGraphics.blit(BG_LOCATION, i + slot1.x, j + slot1.y, this.imageWidth + 16, 0, 16, 16);
        }

        if (!slot2.hasItem()) {
            guiGraphics.blit(BG_LOCATION, i + slot2.x, j + slot2.y, this.imageWidth + 32, 0, 16, 16);
        }

        int k = (int)(41.0F * this.scrollOffs);
        guiGraphics.blit(BG_LOCATION, i + 119, j + 13 + k, 232 + (this.displayPatterns ? 0 : 12), 0, 12, 15);
        Lighting.setupForFlatItems();
        if (this.resultBannerPatterns != null && !this.hasMaxPatterns) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((float)(i + 139), (float)(j + 52), 0.0F);
            guiGraphics.pose().scale(24.0F, -24.0F, 1.0F);
            guiGraphics.pose().translate(0.5F, 0.5F, 0.5F);
            float f = 0.6666667F;
            guiGraphics.pose().scale(f, -f, -f);
            this.flag.xRot = 0.0F;
            this.flag.y = -32.0F;

            if (slot.getItem().getItem() instanceof BannerClaimItem bannerClaimItem)
                BannerClaimRenderer.renderPatterns(guiGraphics.pose(), guiGraphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, this.flag, BannerClaimRenderer.getBannerTierTexture(bannerClaimItem.getTier()), true, this.resultBannerPatterns);
            else
                BannerRenderer.renderPatterns(guiGraphics.pose(), guiGraphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, this.flag, ModelBakery.BANNER_BASE, true, this.resultBannerPatterns);

            guiGraphics.pose().popPose();
            guiGraphics.flush();
        } else if (this.hasMaxPatterns) {
            guiGraphics.blit(BG_LOCATION, i + slot3.x - 2, j + slot3.y - 2, this.imageWidth, 17, 17, 16);
        }

        if (this.displayPatterns) {
            int l2 = i + 60;
            int l = j + 13;
            List<Holder<BannerPattern>> list = this.menu.getSelectablePatterns();

            label64:
            for(int i1 = 0; i1 < 4; ++i1) {
                for(int j1 = 0; j1 < 4; ++j1) {
                    int k1 = i1 + this.startRow;
                    int l1 = k1 * 4 + j1;
                    if (l1 >= list.size()) {
                        break label64;
                    }

                    int i2 = l2 + j1 * 14;
                    int j2 = l + i1 * 14;
                    boolean flag = mouseX >= i2 && mouseY >= j2 && mouseX < i2 + 14 && mouseY < j2 + 14;
                    int k2;
                    if (l1 == this.menu.getSelectedBannerPatternIndex()) {
                        k2 = this.imageHeight + 14;
                    } else if (flag) {
                        k2 = this.imageHeight + 28;
                    } else {
                        k2 = this.imageHeight;
                    }

                    guiGraphics.blit(BG_LOCATION, i2, j2, 0, k2, 14, 14);
                    this.renderPattern(guiGraphics, list.get(l1), i2, j2);
                }
            }
        }

        Lighting.setupFor3DItems();
        ci.cancel();
    }

    @Inject(method = "containerChanged", at = @At("HEAD"), cancellable = true)
    private void bc_containerChanged(CallbackInfo ci) {
        ItemStack itemstack = this.menu.getResultSlot().getItem();
        if (itemstack.isEmpty()) {
            this.resultBannerPatterns = null;
        } else {
            if (itemstack.getItem() instanceof BannerClaimItem)
                this.resultBannerPatterns = BannerClaimBlockEntity.createPatterns(BannerClaimBlockEntity.getItemPatterns(itemstack));
            else
                this.resultBannerPatterns = BannerBlockEntity.createPatterns(((BannerItem)itemstack.getItem()).getColor(), BannerBlockEntity.getItemPatterns(itemstack));
        }

        ItemStack itemstack1 = this.menu.getBannerSlot().getItem();
        ItemStack itemstack2 = this.menu.getDyeSlot().getItem();
        ItemStack itemstack3 = this.menu.getPatternSlot().getItem();
        CompoundTag compoundtag = BlockItem.getBlockEntityData(itemstack1);
        this.hasMaxPatterns = compoundtag != null && compoundtag.contains("Patterns", 9) && !itemstack1.isEmpty() && compoundtag.getList("Patterns", 10).size() >= 6;
        if (this.hasMaxPatterns) {
            this.resultBannerPatterns = null;
        }

        if (!ItemStack.matches(itemstack1, this.bannerStack) || !ItemStack.matches(itemstack2, this.dyeStack) || !ItemStack.matches(itemstack3, this.patternStack)) {
            this.displayPatterns = !itemstack1.isEmpty() && !itemstack2.isEmpty() && !this.hasMaxPatterns && !this.menu.getSelectablePatterns().isEmpty();
        }

        if (this.startRow >= this.totalRowCount()) {
            this.startRow = 0;
            this.scrollOffs = 0.0F;
        }

        this.bannerStack = itemstack1.copy();
        this.dyeStack = itemstack2.copy();
        this.patternStack = itemstack3.copy();
        ci.cancel();
    }
}
