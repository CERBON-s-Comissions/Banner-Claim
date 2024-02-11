package com.cerbon.banner_claim.mixin.recipe;

import com.cerbon.banner_claim.item.custom.BannerClaimItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BannerDuplicateRecipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BannerDuplicateRecipe.class)
public class BannerDuplicateRecipeMixin {

    @Inject(method = "matches(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/world/level/Level;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"), cancellable = true)
    private void bc_preventDuplicationIfBannerClaim(CraftingContainer inv, Level level, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 2) ItemStack itemStack2) {
        if (itemStack2.getItem() instanceof BannerClaimItem)
            cir.setReturnValue(false);
    }
}
