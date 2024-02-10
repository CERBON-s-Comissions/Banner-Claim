package com.cerbon.banner_claim.block;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.block.BannerClaimBlock;
import com.cerbon.banner_claim.block.custom.block.WallBannerClaimBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BannerClaim.MOD_ID);

    public static final RegistryObject<Block> IRON_BANNER = BLOCKS.register("iron_banner", () -> new BannerClaimBlock(BannerTier.IRON, BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON).instrument(NoteBlockInstrument.BASS).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL).noCollission().forceSolidOn()));
    public static final RegistryObject<Block> WALL_IRON_BANNER = BLOCKS.register("wall_iron_banner", () -> new WallBannerClaimBlock(BannerTier.IRON, BlockBehaviour.Properties.copy(BCBlocks.IRON_BANNER.get()).dropsLike(BCBlocks.IRON_BANNER.get())));

    public static final RegistryObject<Block> GOLD_BANNER = BLOCKS.register("gold_banner", () -> new BannerClaimBlock(BannerTier.GOLD, BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).requiresCorrectToolForDrops().strength(3.0F, 6.0F).sound(SoundType.METAL).noCollission().forceSolidOn()));
    public static final RegistryObject<Block> WALL_GOLD_BANNER = BLOCKS.register("wall_gold_banner", () -> new WallBannerClaimBlock(BannerTier.GOLD, BlockBehaviour.Properties.copy(BCBlocks.GOLD_BANNER.get()).dropsLike(BCBlocks.GOLD_BANNER.get())));

    public static final RegistryObject<Block> EMERALD_BANNER = BLOCKS.register("emerald_banner", () -> new BannerClaimBlock(BannerTier.EMERALD, BlockBehaviour.Properties.of().mapColor(MapColor.EMERALD).instrument(NoteBlockInstrument.BIT).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL).noCollission().forceSolidOn()));
    public static final RegistryObject<Block> WALL_EMERALD_BANNER = BLOCKS.register("wall_emerald_banner", () -> new WallBannerClaimBlock(BannerTier.EMERALD, BlockBehaviour.Properties.copy(BCBlocks.EMERALD_BANNER.get()).dropsLike(BCBlocks.EMERALD_BANNER.get())));

    public static final RegistryObject<Block> DIAMOND_BANNER = BLOCKS.register("diamond_banner", () -> new BannerClaimBlock(BannerTier.DIAMOND, BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL).noCollission().forceSolidOn()));
    public static final RegistryObject<Block> WALL_DIAMOND_BANNER = BLOCKS.register("wall_diamond_banner", () -> new WallBannerClaimBlock(BannerTier.DIAMOND, BlockBehaviour.Properties.copy(BCBlocks.DIAMOND_BANNER.get()).dropsLike(BCBlocks.DIAMOND_BANNER.get())));

    public static final RegistryObject<Block> NETHERITE_BANNER = BLOCKS.register("netherite_banner", () -> new BannerClaimBlock(BannerTier.NETHERITE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK).noCollission().forceSolidOn()));
    public static final RegistryObject<Block> WALL_NETHERITE_BANNER = BLOCKS.register("wall_netherite_banner", () -> new WallBannerClaimBlock(BannerTier.NETHERITE, BlockBehaviour.Properties.copy(BCBlocks.NETHERITE_BANNER.get()).dropsLike(BCBlocks.NETHERITE_BANNER.get())));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
