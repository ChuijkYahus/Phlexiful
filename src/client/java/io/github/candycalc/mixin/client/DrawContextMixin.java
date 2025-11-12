package io.github.candycalc.mixin.client;

import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.client.render.PatternRenderer;
import at.petrak.hexcasting.common.lib.HexAttributes;
import io.github.candycalc.util.PatternRenderHelpers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
    @Shadow
    @Final
    private MatrixStack matrices;

    @Inject(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "HEAD"))
    private void drawItemInSlotInject(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null) {
            if (MinecraftClient.getInstance().player.getAttributeValue(HexAttributes.SCRY_SIGHT) > 0 && stack.getItem() instanceof IotaHolderItem) {
                NbtCompound iotaTag = ((IotaHolderItem) stack.getItem()).readIotaTag(stack);
                if (iotaTag != null && Objects.equals(iotaTag.getString("hexcasting:type"), "hexcasting:pattern")) {
                    MatrixStack matrixStack = this.matrices;

                    matrixStack.push();
                    matrixStack.translate(x - 2, y - 2, 170);
                    matrixStack.scale(16f, 16f, 1f);

                    PatternRenderer.renderPattern(
                            HexPattern.fromNBT(iotaTag.getCompound("hexcasting:data")),
                            matrixStack,
                            PatternRenderHelpers.Companion.getINVENTORY_SCRY_SETTINGS(),
                            PatternRenderHelpers.Companion.getINVENTORY_SCRY_COLOR(),
                            0,
                            64);

                    matrixStack.pop();
                }
            }
        }
    }
}