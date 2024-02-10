package com.cerbon.banner_claim.util.mixin;

import com.cerbon.banner_claim.block.custom.entity.BannerClaimBlockEntity;

public interface IEntityMixin {
    BannerClaimBlockEntity getBannerClaimBlockEntity();

    void setBannerClaimBlockEntity(BannerClaimBlockEntity bannerClaimBlockEntity);
}
