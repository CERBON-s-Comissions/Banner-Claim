package com.cerbon.banner_claim.misc.custom;

import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.cerbon.banner_claim.item.custom.BannerClaimItem;
import com.cerbon.banner_claim.misc.BCRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BannerClaimDuplicationRecipe extends CustomRecipe {

    public BannerClaimDuplicationRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(CraftingContainer inv, @NotNull Level level) {
        BannerTier bannerTier = null;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack2 = inv.getItem(i);

            if (!itemstack2.isEmpty()) {
                Item item = itemstack2.getItem();

                if (!(item instanceof BannerClaimItem bannerClaimItem))
                    return false;

                if (bannerTier == null) {
                    bannerTier = bannerClaimItem.getTier();

                } else if (bannerTier != bannerClaimItem.getTier())
                    return false;

                int j = BannerClaimBlockEntity.getPatternCount(itemstack2);
                if (j > 6) {
                    return false;
                }

                if (j > 0) {
                    if (itemstack != null) {
                        return false;
                    }

                    itemstack = itemstack2;
                } else {
                    if (itemstack1 != null) {
                        return false;
                    }

                    itemstack1 = itemstack2;
                }
            }
        }

        return itemstack != null && itemstack1 != null;
    }

    public @NotNull ItemStack assemble(CraftingContainer container, @NotNull RegistryAccess registryAccess) {
        for(int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!itemstack.isEmpty()) {
                int j = BannerClaimBlockEntity.getPatternCount(itemstack);
                if (j > 0 && j <= 6) {
                    return itemstack.copyWithCount(1);
                }
            }
        }

        return ItemStack.EMPTY;
    }

    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

        for(int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.hasCraftingRemainingItem()) {
                    nonnulllist.set(i, itemstack.getCraftingRemainingItem());
                } else if (itemstack.hasTag() && BannerClaimBlockEntity.getPatternCount(itemstack) > 0) {
                    nonnulllist.set(i, itemstack.copyWithCount(1));
                }
            }
        }

        return nonnulllist;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return BCRecipes.BANNER_CLAIM_DUPLICATION_RECIPE.get();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }
}
