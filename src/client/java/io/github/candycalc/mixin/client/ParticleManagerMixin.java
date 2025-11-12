package io.github.candycalc.mixin.client;

import at.petrak.hexcasting.api.HexAPI;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.candycalc.interop.PhlexInterop;
import io.github.candycalc.registry.ItemRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {

    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
    private void addParticle(Particle particle, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            //  check if welding mask is on head
            if (player.getInventory().getArmorStack(EquipmentSlot.HEAD.getEntitySlotId()).isOf(ItemRegistry.Companion.getWELDING_MASK())) {
                //  stop hexcasting particles from spawning
                if (particle.getType().toString().equals(HexAPI.MOD_ID + ":conjure")) ci.cancel();
            }
            //  if Trinkets is installed
            if (FabricLoader.getInstance().isModLoaded(PhlexInterop.TRINKETS_API_ID)) {
                //  check if welding mask is equipped as a trinket
                if (TrinketsApi.getTrinketComponent(player).isPresent() && TrinketsApi.getTrinketComponent(player).get().isEquipped(ItemRegistry.Companion.getWELDING_MASK())) {
                    //  stop particle
                    if (particle.getType().toString().equals(HexAPI.MOD_ID + ":conjure")) ci.cancel();
                }
            }
        }
    }
}
