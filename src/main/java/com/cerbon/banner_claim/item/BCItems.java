package com.cerbon.banner_claim.item;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.block.BCBlocks;
import com.cerbon.banner_claim.item.custom.BannerClaimItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BannerClaim.MOD_ID);

    public static final RegistryObject<Item> IRON_BANNER = ITEMS.register("iron_banner", () -> new BannerClaimItem(BCBlocks.IRON_BANNER.get(), BCBlocks.WALL_IRON_BANNER.get(), new Item.Properties().stacksTo(16)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
