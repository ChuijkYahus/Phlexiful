package io.github.candycalc.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.candycalc.Phlexiful;
import io.github.candycalc.registry.ItemRegistry;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;


@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"))
    private void injectRender(ItemStack stack, ItemDisplayContext itemDisplayContext, boolean leftHanded, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, BakedModel model, CallbackInfo ci, @Local LocalRef<ItemStack> itemStackLocalRef, @Local LocalRef<BakedModel> bakedModelLocalRef) throws NoSuchFieldException {
        if (itemDisplayContext == ItemDisplayContext.HEAD && stack.is(ItemRegistry.WELDING_MASK.get())) {
            bakedModelLocalRef.set(((ItemRendererAccessor) this).mccourse$getModelShaper().getModelManager().getModel(new ModelResourceLocation(Phlexiful.MOD_ID, "welding_mask_on_head", "inventory")));
        }
    }
}
