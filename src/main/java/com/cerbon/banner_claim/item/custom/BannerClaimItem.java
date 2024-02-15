package com.cerbon.banner_claim.item.custom;

import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.block.BannerClaimBlock;
import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.cerbon.banner_claim.client.block.entity.render.BannerClaimItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.NonNullLazy;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BannerClaimItem extends BannerItem {

    public BannerClaimItem(Block block, Block wallBlock, Properties properties) {
        super(block, wallBlock, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy
                    .of(() -> BannerClaimItemRenderer.INSTANCE);

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer.get();
            }
        });
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        if (context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.WATER_CAULDRON)) {
            if (BannerClaimBlockEntity.getPatternCount(context.getItemInHand()) <= 0) {
                return InteractionResult.PASS;
            } else {
                if (!context.getLevel().isClientSide) {
                    ItemStack itemstack = context.getItemInHand().copyWithCount(1);
                    BannerClaimBlockEntity.removeLastPattern(itemstack);
                    if (context.getPlayer() != null && !context.getPlayer().getAbilities().instabuild) {
                        context.getItemInHand().shrink(1);
                    }

                    if (context.getItemInHand().isEmpty()) {
                        context.getPlayer().setItemInHand(context.getHand(), itemstack);
                    } else if (context.getPlayer().getInventory().add(itemstack)) {
                        context.getPlayer().inventoryMenu.sendAllDataToRemote();
                    } else {
                        context.getPlayer().drop(itemstack, false);
                    }

                    context.getPlayer().awardStat(Stats.CLEAN_BANNER);
                    LayeredCauldronBlock.lowerFillLevel(context.getLevel().getBlockState(context.getClickedPos()), context.getLevel(), context.getClickedPos());
                }

                return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
            }
        }

        return super.useOn(context);
    }

    public BannerTier getTier() {
        return ((BannerClaimBlock) this.getBlock()).getTier();
    }
}
