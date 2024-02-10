package com.cerbon.banner_claim.item;

import com.cerbon.banner_claim.BannerClaim;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BCTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BannerClaim.MOD_ID);

    public static RegistryObject<CreativeModeTab> BANNER_CLAIM = CREATIVE_MODE_TABS.register("banner_claim",
            ()-> CreativeModeTab.builder()
                    .icon(()-> new ItemStack(BCItems.IRON_BANNER.get()))
                    .title(Component.translatable("creativemodetab." + BannerClaim.MOD_ID + ".banner_claim_tab"))
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
