package com.cerbon.banner_claim.block;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.block.BannerClaimBlock;
import com.cerbon.banner_claim.block.custom.block.WallBannerClaimBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BannerClaim.MOD_ID);

    public static final RegistryObject<Block> IRON_BANNER = BLOCKS.register("iron_banner", () -> new BannerClaimBlock(BannerTier.IRON, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noCollission().forceSolidOn()));
    public static final RegistryObject<Block> WALL_IRON_BANNER = BLOCKS.register("wall_iron_banner", () -> new WallBannerClaimBlock(BannerTier.IRON, BlockBehaviour.Properties.copy(IRON_BANNER.get()).dropsLike(IRON_BANNER.get())));

    public static final RegistryObject<Block> GOLD_BANNER = BLOCKS.register("gold_banner", () -> new BannerClaimBlock(BannerTier.GOLD, BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).noCollission().forceSolidOn()));
    public static final RegistryObject<Block> WALL_GOLD_BANNER = BLOCKS.register("wall_gold_banner", () -> new WallBannerClaimBlock(BannerTier.GOLD, BlockBehaviour.Properties.copy(GOLD_BANNER.get()).dropsLike(GOLD_BANNER.get())));

    public static final RegistryObject<Block> EMERALD_BANNER = BLOCKS.register("emerald_banner", () -> new BannerClaimBlock(BannerTier.EMERALD, BlockBehaviour.Properties.copy(Blocks.EMERALD_BLOCK).noCollission().forceSolidOn()));
    public static final RegistryObject<Block> WALL_EMERALD_BANNER = BLOCKS.register("wall_emerald_banner", () -> new WallBannerClaimBlock(BannerTier.EMERALD, BlockBehaviour.Properties.copy(EMERALD_BANNER.get()).dropsLike(EMERALD_BANNER.get())));

    public static final RegistryObject<Block> DIAMOND_BANNER = BLOCKS.register("diamond_banner", () -> new BannerClaimBlock(BannerTier.DIAMOND, BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).noCollission().forceSolidOn()));
    public static final RegistryObject<Block> WALL_DIAMOND_BANNER = BLOCKS.register("wall_diamond_banner", () -> new WallBannerClaimBlock(BannerTier.DIAMOND, BlockBehaviour.Properties.copy(DIAMOND_BANNER.get()).dropsLike(DIAMOND_BANNER.get())));

    public static final RegistryObject<Block> NETHERITE_BANNER = BLOCKS.register("netherite_banner", () -> new BannerClaimBlock(BannerTier.NETHERITE, BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).noCollission().forceSolidOn()));
    public static final RegistryObject<Block> WALL_NETHERITE_BANNER = BLOCKS.register("wall_netherite_banner", () -> new WallBannerClaimBlock(BannerTier.NETHERITE, BlockBehaviour.Properties.copy(NETHERITE_BANNER.get()).dropsLike(NETHERITE_BANNER.get())));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
