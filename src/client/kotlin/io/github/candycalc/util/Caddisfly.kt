package io.github.candycalc.util

import at.petrak.hexcasting.api.HexAPI
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.common.lib.HexItems
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.ColorHelper

class Caddisfly {
    companion object {
        //TODO("Make this data controlled so it's easy to pair nbt components with a layer")
        //  Hardcoding goes brrrrr
        val CYPHER_DIAMOND: Identifier = Identifier(HexAPI.MOD_ID,"textures/item/cad/0_cypher_overlay.png")
        //val ANCIENT_CYPHER_DIAMOND: Identifier = Identifier(HexAPI.MOD_ID,"textures/item/cad/0_ancient_cypher_overlay.png")
        val TRINKET_DIAMOND: Identifier = Identifier(HexAPI.MOD_ID,"textures/item/cad/0_trinket_overlay.png")
        val ARTIFACT_DIAMOND: Identifier = Identifier(HexAPI.MOD_ID,"textures/item/cad/0_artifact_overlay.png")
        val FOCUS_DIAMOND: Identifier = Identifier(HexAPI.MOD_ID,"textures/item/focus_overlay.png")

        fun caddisflyify(stack: ItemStack): ItemStack {
            checkNotNull(stack.nbt)
            val returnValue = ItemStack.fromNbt(stack.getNbt()!!.getCompound(PhlexUtil.CADDISFLY_TAG))

            if (stack.nbt!!.contains("Enchantments")) {
                returnValue.setSubNbt("Enchantments", stack.getNbt()!!.get("Enchantments"))
            } else {
                returnValue.removeSubNbt("Enchantments")
            }

            return returnValue
        }

        fun renderCastingDiamond(context: DrawContext, x: Int, y: Int, itemStack: ItemStack) {
            if (itemStack.orCreateNbt.contains(PhlexUtil.CADDISFLY_TAG) && (
                        itemStack.isOf(HexItems.CYPHER) ||
                        itemStack.isOf(HexItems.TRINKET) ||
                        itemStack.isOf(HexItems.ARTIFACT) ||
                        itemStack.isOf(HexItems.FOCUS)
                    )) {

                RenderSystem.disableDepthTest()
                RenderSystem.enableBlend()
                RenderSystem.defaultBlendFunc()

                var texture: Identifier? = null

                if (itemStack.orCreateNbt.contains("patterns")) {
                    if (itemStack.isOf(HexItems.TRINKET)) {
                        texture = TRINKET_DIAMOND
                    } else if (itemStack.isOf(HexItems.ARTIFACT)) {
                        texture = ARTIFACT_DIAMOND
                    } else {
                        texture = CYPHER_DIAMOND
                    }
                } else if (itemStack.orCreateNbt.contains("data") && itemStack.isOf(HexItems.FOCUS)) {
                    texture = FOCUS_DIAMOND
                    val fuckedColorFormat: Int = IotaType.deserialize(itemStack.orCreateNbt.getCompound("data"), null).type.color()
                    RenderSystem.setShaderColor(
                        ColorHelper.Abgr.getRed(fuckedColorFormat)/255f,
                        ColorHelper.Abgr.getGreen(fuckedColorFormat)/255f,
                        ColorHelper.Abgr.getBlue(fuckedColorFormat)/255f,
                        1f)
                }

                if (texture != null) {
                    context.drawTexture(texture, x, y, 0f, 0f, 16, 16, 16, 16)
                }
                RenderSystem.enableDepthTest()
                RenderSystem.disableBlend()
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f)
            }
        }
    }
}