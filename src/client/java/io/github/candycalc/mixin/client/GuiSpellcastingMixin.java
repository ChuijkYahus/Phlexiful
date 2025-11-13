package io.github.candycalc.mixin.client;

import at.petrak.hexcasting.client.gui.GuiSpellcasting;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSpellcasting.class)
public class GuiSpellcastingMixin {
    @Unique
    private int counter = 0;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I", ordinal = 0))
    private void injectNumbers(DrawContext graphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci, @Local MinecraftClient mc) {
        graphics.drawTextWithShadow(mc.textRenderer, String.format("%02d", counter) + ".", 5, 7, -1);
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I", ordinal = 0), index = 2)
    private int injectOffset(int y) {
        counter += 1;
        return y + 17;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V", ordinal = 0))
    private void injectCounterReset(DrawContext graphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        counter = 0;
    }
}
