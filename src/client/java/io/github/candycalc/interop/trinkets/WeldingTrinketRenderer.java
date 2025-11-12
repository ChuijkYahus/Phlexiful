package io.github.candycalc.interop.trinkets;

import at.petrak.hexcasting.common.lib.HexItems;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import io.github.candycalc.registry.ItemRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

/**
 * @author WireSegal
 * Created at 9:50 AM on 7/25/22.
 */
public class WeldingTrinketRenderer implements TrinketRenderer {
    @Override
    @SuppressWarnings("unchecked")
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> model,
                       MatrixStack matrices, VertexConsumerProvider multiBufferSource, int light, LivingEntity entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
        if (stack.isOf(ItemRegistry.Companion.getWELDING_MASK().asItem()) &&
                model instanceof PlayerEntityModel playerModel &&
                entity instanceof AbstractClientPlayerEntity player) {

            // from https://github.com/Creators-of-Create/Create/blob/ee33823ed0b5084af10ed131a1626ce71db4c07e/src/main/java/com/simibubi/create/compat/curios/GogglesCurioRenderer.java

            // Translate and rotate with our head
            matrices.push();
            TrinketRenderer.followBodyRotations(entity, playerModel);
            TrinketRenderer.translateToFace(matrices, playerModel, player, headYaw, headPitch);

            // Translate and scale to our head
            matrices.translate(0, 0, 0.3);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0f));
            matrices.scale(0.625f, 0.625f, 0.625f);

            // Render
            var instance = MinecraftClient.getInstance();
            instance.getItemRenderer().renderItem(stack, ModelTransformationMode.HEAD,
                    light, OverlayTexture.DEFAULT_UV, matrices, multiBufferSource, instance.world, 0);
            matrices.pop();
        }
    }

}

//  I got this from https://github.com/FallingColors/HexMod/blob/main/Fabric/src/main/java/at/petrak/hexcasting/fabric/interop/trinkets/LensTrinketRenderer.java#L23
//  I basically just switched up some names