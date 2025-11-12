package io.github.candycalc.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.github.candycalc.util.Caddisfly;
import io.github.candycalc.util.PhlexUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeadFeatureRenderer.class)
abstract class HeadFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> {
    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", shift = At.Shift.BEFORE))
    private void renderInject(
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int i,
            T livingEntity,
            float f,
            float g,
            float h,
            float j,
            float k,
            float l,
            CallbackInfo ci,
            @Local(ordinal = 0) LocalRef<ItemStack> itemStackLocalRef) {
        if (itemStackLocalRef.get().getOrCreateNbt().contains(PhlexUtil.CADDISFLY_TAG)) {
            itemStackLocalRef.set(Caddisfly.Companion.caddisflyify(itemStackLocalRef.get()));
        }
    }
}
