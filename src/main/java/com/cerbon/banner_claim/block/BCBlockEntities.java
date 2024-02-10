package com.cerbon.banner_claim.block;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.cerbon.banner_claim.client.block.entity.render.BannerClaimRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BCBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCKS_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BannerClaim.MOD_ID);

    public static final RegistryObject<BlockEntityType<BannerClaimBlockEntity>> BANNER_CLAIM = BLOCKS_ENTITIES.register("banner_claim", () ->
            BlockEntityType.Builder.of(BannerClaimBlockEntity::new, BCBlocks.IRON_BANNER.get(), BCBlocks.WALL_IRON_BANNER.get(), BCBlocks.GOLD_BANNER.get(), BCBlocks.WALL_GOLD_BANNER.get(), BCBlocks.EMERALD_BANNER.get(), BCBlocks.WALL_EMERALD_BANNER.get()).build(null));

    @OnlyIn(Dist.CLIENT)
    public static void initClient() {
        BlockEntityRenderers.register(
                BCBlockEntities.BANNER_CLAIM.get(),
                BannerClaimRenderer::new
        );
    }

    public static void register(IEventBus eventBus){
        BLOCKS_ENTITIES.register(eventBus);
    }
}
