package com.cerbon.banner_claim.patterns;

import com.cerbon.banner_claim.BannerClaim;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BCPatterns {
    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registries.BANNER_PATTERN, BannerClaim.MOD_ID);

    public static final RegistryObject<BannerPattern> IRON_BANNER_BASE = BANNER_PATTERNS.register("iron_banner_base", () -> new BannerPattern("irb"));

    public static void register(IEventBus eventBus) {
        BANNER_PATTERNS.register(eventBus);
    }
}
