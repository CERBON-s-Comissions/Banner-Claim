package com.cerbon.banner_claim.block.custom.entity;

import com.cerbon.banner_claim.block.BCBlockEntities;
import com.cerbon.banner_claim.block.custom.BannerTier;
import com.cerbon.banner_claim.block.custom.block.AbstractBannerClaimBlock;
import com.cerbon.banner_claim.block.custom.block.BannerClaimBlock;
import com.cerbon.banner_claim.particle.BCParticles;
import com.cerbon.banner_claim.util.mixin.IEntityMixin;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;


public class BannerClaimBlockEntity extends BlockEntity implements Nameable {
    public static final String TAG_PATTERNS = "Patterns";
    public static final String TAG_PATTERN = "Pattern";
    public static final String TAG_COLOR = "Color";

    @Nullable
    private Component name;
    private BannerTier bannerTier;
    private UUID ownerUUID;

    public List<Player> playersInBox;

    @Nullable private ListTag itemPatterns;
    @Nullable private List<Pair<Holder<BannerPattern>, DyeColor>> patterns;

    public BannerClaimBlockEntity(BlockPos pos, BlockState blockState) {
        super(BCBlockEntities.BANNER_CLAIM.get(), pos, blockState);
        this.bannerTier = ((AbstractBannerClaimBlock) blockState.getBlock()).getTier();
    }

    public BannerClaimBlockEntity(BlockPos pos, BlockState blockState, BannerTier bannerTier) {
        this(pos, blockState);
        this.bannerTier = bannerTier;
    }

    public void fromItem(ItemStack pStack, BannerTier tier) {
        this.bannerTier = tier;
        this.fromItem(pStack);
    }

    public void fromItem(ItemStack item) {
        this.itemPatterns = getItemPatterns(item);
        this.patterns = null;
        this.name = item.hasCustomHoverName() ? item.getHoverName() : null;
    }

    @Nullable
    public static ListTag getItemPatterns(ItemStack stack) {
        ListTag listtag = null;
        CompoundTag compoundtag = BlockItem.getBlockEntityData(stack);

        if (compoundtag != null && compoundtag.contains(TAG_PATTERNS, 9))
            listtag = compoundtag.getList(TAG_PATTERNS, 10).copy();

        return listtag;
    }

    @Override
    public @NotNull Component getName() {
        return this.name != null ? this.name : Component.translatable("block.minecraft.banner");
    }

    @Nullable
    public Component getCustomName() {
        return this.name;
    }

    public void setCustomName(Component pName) {
        this.name = pName;
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        if (this.itemPatterns != null)
            tag.put(TAG_PATTERNS, this.itemPatterns);

        if (this.name != null)
            tag.putString("CustomName", Component.Serializer.toJson(this.name));

        if (this.ownerUUID != null)
            tag.putUUID("Owner", ownerUUID);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        if (tag.contains("CustomName", 8))
            this.name = Component.Serializer.fromJson(tag.getString("CustomName"));

        this.itemPatterns = tag.getList(TAG_PATTERNS, 10);
        this.patterns = null;

        if (tag.contains("Owner"))
            ownerUUID = tag.getUUID("Owner");
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    /**
     * @return the amount of patterns stored in the given ItemStack. Defaults to zero if none are stored.
     */
    public static int getPatternCount(ItemStack pStack) {
        CompoundTag compoundtag = BlockItem.getBlockEntityData(pStack);
        return compoundtag != null && compoundtag.contains(TAG_PATTERNS) ? compoundtag.getList(TAG_PATTERNS, 10).size() : 0;
    }

    /**
     * @return the patterns for this banner.
     */
    public List<Pair<Holder<BannerPattern>, DyeColor>> getPatterns() {
        if (this.patterns == null)
            this.patterns = createPatterns(this.itemPatterns);

        return this.patterns;
    }

    public static List<Pair<Holder<BannerPattern>, DyeColor>> createPatterns(@Nullable ListTag listTag) {
        List<Pair<Holder<BannerPattern>, DyeColor>> list = Lists.newArrayList();

        if (listTag != null) {
            for(int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundtag = listTag.getCompound(i);
                Holder<BannerPattern> holder = BannerPattern.byHash(compoundtag.getString(TAG_PATTERN));

                if (holder != null) {
                    int j = compoundtag.getInt(TAG_COLOR);
                    list.add(Pair.of(holder, DyeColor.byId(j)));
                }
            }
        }

        return list;
    }

    /**
     * Removes all banner data from the given ItemStack.
     */
    public static void removeLastPattern(ItemStack stack) {
        CompoundTag compoundtag = BlockItem.getBlockEntityData(stack);

        if (compoundtag != null && compoundtag.contains(TAG_PATTERNS, 9)) {
            ListTag listtag = compoundtag.getList(TAG_PATTERNS, 10);

            if (!listtag.isEmpty()) {
                listtag.remove(listtag.size() - 1);

                if (listtag.isEmpty())
                    compoundtag.remove(TAG_PATTERNS);

                BlockItem.setBlockEntityData(stack, BCBlockEntities.BANNER_CLAIM.get(), compoundtag);
            }
        }
    }

    public ItemStack getItem() {
        ItemStack itemstack = new ItemStack(BannerClaimBlock.byTier(this.bannerTier));

        if (this.itemPatterns != null && !this.itemPatterns.isEmpty()) {
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.put(TAG_PATTERNS, this.itemPatterns.copy());
            BlockItem.setBlockEntityData(itemstack, BCBlockEntities.BANNER_CLAIM.get(), compoundtag);
        }

        if (this.name != null)
            itemstack.setHoverName(this.name);

        return itemstack;
    }

    public void setOwner(UUID playerUUID) {
        ownerUUID = playerUUID;
    }

    public Player getOwner() {
        if (level != null)
            return level.getPlayerByUUID(ownerUUID);

        return null;
    }

    public BannerTier getBannerTier() {
        return this.bannerTier;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BannerClaimBlockEntity bannerClaim) {
        AABB box = bannerClaim.getAffectingBox(level, VecUtils.asVec3(pos), bannerClaim.getBannerTier());
        bannerClaim.playersInBox = level.getEntitiesOfClass(Player.class, box);

        if (level.isClientSide) {
            if (level.random.nextFloat() <= 0.1f) {
                for (Player player : bannerClaim.playersInBox) {
                    for (double x : List.of(box.minX, box.maxX)) {
                        for (double z = box.minZ; z <= box.maxZ; z++)
                            Particles.particleFactory.build(randYPos(x, player, z), Vec3.ZERO);
                    }

                    for (double z : List.of(box.minZ, box.maxZ)) {
                        for (double x = box.minX; x <= box.maxX; x++)
                            Particles.particleFactory.build(randYPos(x, player, z), Vec3.ZERO);
                    }
                }
            }
        } else {
            for (Player player : bannerClaim.playersInBox)
                ((IEntityMixin) player).setBannerClaimBlockEntity(bannerClaim);
        }
    }

    private static Vec3 randYPos(double x, Player player, double z) {
        return new Vec3(x, player.getY() + RandomUtils.randDouble(0.5) + 1, z);
    }

    public AABB getAffectingBox(Level level, Vec3 pos, BannerTier tier) {
        return new AABB(pos.x, pos.y, pos.z, pos.x + 1, level.getHeight(), pos.z + 1).inflate(getBannerTierRange(tier), 0.0, getBannerTierRange(tier));
    }

    public static int getBannerTierRange(BannerTier tier) {
        return switch (tier) {
            case IRON -> 8;
            case GOLD -> 16;
            case EMERALD -> 32;
            case DIAMOND -> 64;
            case NETHERITE -> 128;
        };
    }

    private static class Particles {
        private static final ClientParticleBuilder particleFactory = new ClientParticleBuilder(BCParticles.LINE.get())
                .color(Vec3Colors.GOLD)
                .brightness(15728880)
                .colorVariation(0.2)
                .scale(0.075f);
    }
}
