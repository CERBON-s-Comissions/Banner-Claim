package com.cerbon.banner_claim.particle;

import com.cerbon.banner_claim.BannerClaim;
import com.cerbon.cerbons_api.api.general.particle.SimpleParticle;
import com.cerbon.cerbons_api.api.general.particle.SimpleParticleProvider;
import com.cerbon.cerbons_api.api.static_utilities.Geometries;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BCParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BannerClaim.MOD_ID);

    public static final RegistryObject<SimpleParticleType> LINE = PARTICLE_TYPES.register("line", () -> new SimpleParticleType(true));

    @OnlyIn(Dist.CLIENT)
    public static void initClient(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(
                BCParticles.LINE.get(),
                spriteSet -> new SimpleParticleProvider(
                        spriteSet,
                        particleContext -> new SimpleParticle(particleContext, RandomUtils.range(20, 30), Geometries::buildBillBoardGeometry, true, true)
                )
        );
    }

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
