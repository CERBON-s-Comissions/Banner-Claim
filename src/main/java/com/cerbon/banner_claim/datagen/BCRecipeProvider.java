package com.cerbon.banner_claim.datagen;

import com.cerbon.banner_claim.block.BCBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BCRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public BCRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        ironBannerRecipe(writer);

        upgradeBanner(writer, BCBlocks.IRON_BANNER.get(), BCBlocks.GOLD_BANNER.get(), Items.GOLD_BLOCK, Items.GOLD_INGOT);
        upgradeBanner(writer, BCBlocks.GOLD_BANNER.get(), BCBlocks.EMERALD_BANNER.get(), Items.EMERALD_BLOCK, Items.EMERALD);
        upgradeBanner(writer, BCBlocks.EMERALD_BANNER.get(), BCBlocks.DIAMOND_BANNER.get(), Items.DIAMOND_BLOCK, Items.DIAMOND);
        upgradeBanner(writer, BCBlocks.DIAMOND_BANNER.get(), BCBlocks.NETHERITE_BANNER.get(), Items.NETHERITE_BLOCK, Items.NETHERITE_INGOT);
    }

    private void ironBannerRecipe(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BCBlocks.IRON_BANNER.get())
                .pattern("ISI")
                .pattern("SBS")
                .pattern("ISI")
                .define('I', Items.IRON_BLOCK)
                .define('S', Items.NETHER_STAR)
                .define('B', ItemTags.BANNERS)
                .group("claim_banners")
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .unlockedBy("has_banner", has(ItemTags.BANNERS))
                .save(writer);
    }

    private void upgradeBanner(Consumer<FinishedRecipe> writer, Block from, Block to, Item tierBlock, Item tierIngot) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, to)
                .pattern(" T ")
                .pattern("TBT")
                .pattern(" T ")
                .define('T', tierBlock)
                .define('B', from)
                .group("claim_banners")
                .unlockedBy("has_previous", has(from))
                .unlockedBy("has_ingot", has(tierIngot))
                .save(writer);
    }
}
