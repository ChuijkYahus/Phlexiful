package io.github.candycalc.mixin.client;

import at.petrak.hexcasting.api.HexAPI;
import at.petrak.hexcasting.client.particles.ConjureParticle;
import io.github.candycalc.Phlexiful;
import io.github.candycalc.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin {
    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void createParticle(Particle particle, CallbackInfo ci) {
        AbstractClientPlayer player = Minecraft.getInstance().player;
        if (player != null && player.getInventory().getArmor(EquipmentSlot.HEAD.getIndex()).is(ItemRegistry.WELDING_MASK.get())) {
            if (particle.getRenderType().toString().equals(HexAPI.MOD_ID + ":conjure")) ci.cancel();
        }
    }
}
