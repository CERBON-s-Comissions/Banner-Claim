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

    public static final RegistryObject<Block> IRON_BANNER = BLOCKS.register("iron_banner_claim", () -> new BannerClaimBlock(BannerTier.IRON, BlockBehaviour.Properties.copy(Blocks.BLACK_BANNER)));
    public static final RegistryObject<Block> WALL_IRON_BANNER = BLOCKS.register("wall_iron_banner_claim", () -> new WallBannerClaimBlock(BannerTier.IRON, BlockBehaviour.Properties.copy(Blocks.BLACK_WALL_BANNER)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
