package com.cerbon.banner_claim.event;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.datagen.BCRecipeProvider;
import com.cerbon.banner_claim.item.BCItems;
import com.cerbon.banner_claim.item.BCTabs;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BannerClaim.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BCEvents {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        generator.addProvider(event.includeServer(), new BCRecipeProvider(packOutput));
    }


    @SubscribeEvent
    public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == BCTabs.BANNER_CLAIM.get())
            BCItems.ITEMS.getEntries().forEach(event::accept);
    }
}
