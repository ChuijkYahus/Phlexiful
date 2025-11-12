package io.github.candycalc.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.github.candycalc.util.PhlexUtil;
import io.github.candycalc.util.Caddisfly;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> {

    @Inject(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", shift = At.Shift.BEFORE))
    private void injectRenderer(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            T entity,
            EquipmentSlot armorSlot,
            int light,
            A model,
            CallbackInfo ci,
            @Local(ordinal = 0) LocalRef<ItemStack> itemStackLocalRef
    ) {
        if (itemStackLocalRef.get().getOrCreateNbt().contains(PhlexUtil.CADDISFLY_TAG)) {
            itemStackLocalRef.set(Caddisfly.Companion.caddisflyify(itemStackLocalRef.get()));
        }
    }
}
