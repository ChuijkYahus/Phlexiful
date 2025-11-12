package io.github.candycalc.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.github.candycalc.Phlexiful;
import io.github.candycalc.registry.ItemRegistry;
import io.github.candycalc.util.PhlexUtil;
import io.github.candycalc.util.Caddisfly;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin implements ItemRendererAccessor {

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    private void InjectRenderer(
            ItemStack stack,
            ModelTransformationMode renderMode,
            boolean leftHanded,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            BakedModel model,
            CallbackInfo ci,
            @Local LocalRef<ItemStack> itemStackLocalRef,
            @Local LocalRef<BakedModel> bakedModelLocalRef
    ) {
        //  manage head/inventory models for welding mask
        if (renderMode == ModelTransformationMode.HEAD && stack.isOf(ItemRegistry.Companion.getWELDING_MASK())) {
            bakedModelLocalRef.set(this.accessor$getModels().getModelManager().getModel(new ModelIdentifier(Phlexiful.MOD_ID, "welding_mask_on_head", "inventory")));
        }

        //  manage caddisfly overwriting
        ItemStack originalItem = itemStackLocalRef.get();
        if (originalItem.getOrCreateNbt().contains(PhlexUtil.CADDISFLY_TAG)) {
            ItemStack caddisflyified = Caddisfly.Companion.caddisflyify(originalItem);
            BakedModel bakedModel = MinecraftClient.getInstance().getItemRenderer().getModel(caddisflyified, null, null, 0);
            if (renderMode == ModelTransformationMode.GUI) {
                if (bakedModel.isSideLit()) {
                    DiffuseLighting.enableGuiDepthLighting();
                } else {
                    DiffuseLighting.disableGuiDepthLighting();
                }
            }
            bakedModelLocalRef.set(bakedModel);
            itemStackLocalRef.set(caddisflyified);
        }
    }
}
