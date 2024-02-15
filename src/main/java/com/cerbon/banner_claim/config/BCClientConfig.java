package com.cerbon.banner_claim.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BCClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue SHOW_PARTICLES_AROUND_BANNER;

    static {
        SHOW_PARTICLES_AROUND_BANNER = BUILDER.define("Show Particles Around Banner", true);

        SPEC = BUILDER.build();
    }
}
