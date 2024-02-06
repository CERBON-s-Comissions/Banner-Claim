package com.cerbon.banner_claim;

import com.cerbon.banner_claim.particle.BCParticles;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BannerClaim.MOD_ID)
public class BannerClaim {
    public static final String MOD_ID = "banner_claim";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BannerClaim() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BCParticles.register(modEventBus);
    }
}
