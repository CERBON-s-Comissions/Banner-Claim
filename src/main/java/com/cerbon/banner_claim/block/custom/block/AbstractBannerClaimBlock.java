package com.cerbon.banner_claim.block.custom.block;

import com.cerbon.banner_claim.block.BCBlockEntities;
import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbstractBannerClaimBlock extends AbstractBannerBlock {
    private final BannerTier tier;

    protected AbstractBannerClaimBlock(BannerTier tier, Properties properties) {
        super(DyeColor.WHITE, properties);
        this.tier = tier;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new BannerClaimBlockEntity(pos, state, tier);
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        if (!level.isClientSide) {
            level.getBlockEntity(pos, BCBlockEntities.BANNER_CLAIM.get()).ifPresent(blockEntity -> {
                if (stack.hasCustomHoverName())
                    blockEntity.setCustomName(stack.getHoverName());

                if (placer instanceof Player player)
                    blockEntity.setOwner(player.getUUID());
            });
        }
        else
            level.getBlockEntity(pos, BCBlockEntities.BANNER_CLAIM.get()).ifPresent(blockEntity -> blockEntity.fromItem(stack));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        return blockEntity instanceof BannerClaimBlockEntity bannerClaimBlockEntity ? bannerClaimBlockEntity.getItem() : super.getCloneItemStack(level, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, BCBlockEntities.BANNER_CLAIM.get(), BannerClaimBlockEntity::tick);
    }

    public BannerTier getTier() {
        return tier;
    }
}
