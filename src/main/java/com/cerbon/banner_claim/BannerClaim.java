package com.cerbon.banner_claim;

import com.cerbon.banner_claim.block.BCBlockEntities;
import com.cerbon.banner_claim.block.BCBlocks;
import com.cerbon.banner_claim.config.BCClientConfig;
import com.cerbon.banner_claim.config.BCCommonConfig;
import com.cerbon.banner_claim.item.BCItems;
import com.cerbon.banner_claim.item.BCTabs;
import com.cerbon.banner_claim.misc.BCRecipes;
import com.cerbon.banner_claim.particle.BCParticles;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BannerClaim.MOD_ID)
public class BannerClaim {
    public static final String MOD_ID = "banner_claim";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BannerClaim() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BCBlocks.register(modEventBus);
        BCBlockEntities.register(modEventBus);

        BCTabs.register(modEventBus);
        BCItems.register(modEventBus);

        BCParticles.register(modEventBus);
        BCRecipes.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BCClientConfig.SPEC, MOD_ID + "-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BCCommonConfig.SPEC, MOD_ID + "-common.toml");
    }
}
