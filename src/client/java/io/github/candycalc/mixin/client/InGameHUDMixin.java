package io.github.candycalc.mixin.client;

import io.github.candycalc.util.Caddisfly;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHUDMixin {
    @Inject(method = "renderHotbarItem", at = @At("TAIL"))
    private void renderCastingDiamond(DrawContext context, int x, int y, float f, PlayerEntity player, ItemStack stack, int seed, CallbackInfo ci) {
        Caddisfly.Companion.renderCastingDiamond(context, x, y, stack);
    }
}
