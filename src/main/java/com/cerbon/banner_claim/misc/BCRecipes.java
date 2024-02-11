package com.cerbon.banner_claim.misc;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.misc.custom.BannerClaimDuplicationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BCRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(Registries.RECIPE_SERIALIZER, BannerClaim.MOD_ID);

    public static RegistryObject<RecipeSerializer<?>> BANNER_CLAIM_DUPLICATION_RECIPE = RECIPES.register("crafting_special_banner_claim_duplication",
            () -> new SimpleCraftingRecipeSerializer<>(BannerClaimDuplicationRecipe::new));

    public static void register(IEventBus eventBus) {
        RECIPES.register(eventBus);
    }
}
