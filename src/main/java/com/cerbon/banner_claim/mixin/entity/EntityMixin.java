package com.cerbon.banner_claim.mixin.entity;

import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;
import com.cerbon.banner_claim.util.mixin.IEntityMixin;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Entity.class)
public class EntityMixin implements IEntityMixin {
    @Unique private BannerClaimBlockEntity bannerClaimBlockEntity;

    public BannerClaimBlockEntity getBannerClaimBlockEntity() {
        return bannerClaimBlockEntity;
    }

    public void setBannerClaimBlockEntity(BannerClaimBlockEntity bannerClaimBlockEntity) {
        this.bannerClaimBlockEntity = bannerClaimBlockEntity;
    }
}
