package io.github.candycalc.mixin.client;

import io.github.candycalc.util.Caddisfly;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {
    @Inject(method = "drawSlot", at = @At("TAIL"))
    private void renderCastingDiamond(DrawContext context, Slot slot, CallbackInfo ci) {
        Caddisfly.Companion.renderCastingDiamond(context, slot.x, slot.y, slot.getStack());
    }
}
