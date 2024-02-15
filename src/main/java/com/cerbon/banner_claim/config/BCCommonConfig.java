package com.cerbon.banner_claim.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BCCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> CLAIM_DEPTH;
    public static final ForgeConfigSpec.ConfigValue<Integer> PROTECTION_RANGE;

    public static final ForgeConfigSpec.ConfigValue<Integer> IRON_BANNER_RANGE;
    public static final ForgeConfigSpec.ConfigValue<Integer> GOLD_BANNER_RANGE;
    public static final ForgeConfigSpec.ConfigValue<Integer> EMERALD_BANNER_RANGE;
    public static final ForgeConfigSpec.ConfigValue<Integer> DIAMOND_BANNER_RANGE;
    public static final ForgeConfigSpec.ConfigValue<Integer> NETHERITE_BANNER_RANGE;

    public static final ForgeConfigSpec.ConfigValue<Integer> TIME_TO_ACTIVATE;

    static {
        CLAIM_DEPTH = BUILDER.define("Claim Depth", 10);
        PROTECTION_RANGE = BUILDER.define("Protection Range", 5);

        IRON_BANNER_RANGE = BUILDER.define("Iron Banner Range", 8);
        GOLD_BANNER_RANGE = BUILDER.define("Gold Banner Range", 16);
        EMERALD_BANNER_RANGE = BUILDER.define("Emerald Banner Range", 32);
        DIAMOND_BANNER_RANGE = BUILDER.define("Diamond Banner Range", 64);
        NETHERITE_BANNER_RANGE = BUILDER.define("Netherite Banner Range", 128);

        TIME_TO_ACTIVATE = BUILDER.comment("In seconds!")
                                  .define("Time To Activate", 15);

        SPEC = BUILDER.build();
    }
}
