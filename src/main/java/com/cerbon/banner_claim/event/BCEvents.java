package com.cerbon.banner_claim.event;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.item.BCItems;
import com.cerbon.banner_claim.item.BCTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BannerClaim.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCEvents {

    @SubscribeEvent
    public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == BCTabs.BANNER_CLAIM.get())
            BCItems.ITEMS.getEntries().forEach(event::accept);
    }
}
