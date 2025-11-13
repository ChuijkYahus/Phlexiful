package io.github.candycalc.item.armor

import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.item.IotaHolderItem
import at.petrak.hexcasting.api.item.MediaHolderItem
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.utils.*
import at.petrak.hexcasting.xplat.IXplatAbstractions
import io.github.candycalc.util.ItemStackAccessor
import io.github.candycalc.util.math
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text
import net.minecraft.text.TextColor
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow

//  this item has been a headache from start to finish

const val TAG_MEDIA: String = "hexcasting:media"
const val TAG_MAX_MEDIA: String = "hexcasting:start_media"
const val TAG_DATA: String = "data"
const val TAG_WORN: String = "phlexiful:is_worn" //I don't want to talk about how cursed this is (unless you know a better way pls)

class BatteryPants(type: Type, settings: Settings) : ArmorItem(PhlexArmorMaterials.BATTERY, type, settings), MediaHolderItem {
    //removed trimming of trailing 0s because it was distracting with the constant increase.
    val DUST_AMOUNT: DecimalFormat = DecimalFormat("###,##0.00")
    val HEX_COLOR: TextColor? = TextColor.fromRgb(0xb38ef3)
    //idk why setRoundingMode doesn't chain but it doesn't so we doin' lambda
    val PERCENTAGE: DecimalFormat = {
        val result = DecimalFormat("#####")
        result.setRoundingMode(RoundingMode.DOWN)
        result
    }()

    @Override
    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
    }

    //  MediaHolder stuff
    override fun getMedia(stack: ItemStack): Long {
        //intellij says this cast will never succeed. Don't listen to its lies.
        return (stack as ItemStackAccessor).`phlexiful$getMedia`() ?: 0L
    }

    override fun getMaxMedia(stack: ItemStack): Long {
        return MediaConstants.DUST_UNIT * 5//stack.getLong(TAG_MAX_MEDIA)
    }

    //TODO("Add overcharging via enchantment")
    override fun setMedia(stack: ItemStack, media: Long) {
        (stack as ItemStackAccessor).`phlexiful$setMedia`(MathUtils.clamp(media, 0, getMaxMedia(stack)))
        //stack.putLong(TAG_MEDIA, media)
    }

    fun setMaxMedia(stack: ItemStack, media: Long) {
        return stack.putLong(TAG_MAX_MEDIA, media)
    }

    //TODO make this better somehow maybe???
    override fun canProvideMedia(stack: ItemStack): Boolean {
        return false
    }

    override fun canRecharge(p0: ItemStack?): Boolean {
        return false
    }

    override fun withdrawMedia(stack: ItemStack, cost: Long, simulate: Boolean): Long {
        val pantsIota: Iota? = IXplatAbstractions.INSTANCE.findDataHolder(stack)?.readIota(null)
        val threshold: Double = if (pantsIota == null) -1.0 else (pantsIota as DoubleIota).double
        //Only withdraw media if the amount being withdrawn is below the threshold
        return if (cost <= threshold * MediaConstants.DUST_UNIT || threshold < 0) {
            super.withdrawMedia(stack, cost, simulate)
        } else {
            0
        }
    }

    //  Armor/equipment stuff
    override fun isItemBarVisible(stack: ItemStack): Boolean {
        return getMedia(stack) > 0
    }

    override fun getItemBarStep(stack: ItemStack): Int {
        val media = getMedia(stack)
        val maxMedia = getMaxMedia(stack)
        return mediaBarWidth(media, maxMedia)
    }

    override fun getItemBarColor(stack: ItemStack): Int {
        val media: Long = getMedia(stack)
        val maxMedia: Long = getMaxMedia(stack)

        if (media <= maxMedia) {
            return mediaBarColor(media, maxMedia)
        }

        val amt: Float = (media / maxMedia - 1).toFloat()
        val (r, g, b) = if (amt <= 0.5) {
            intArrayOf(
                math.color_lerp(amt * 2, 254, 239),
                math.color_lerp(amt * 2, 203, 218),
                math.color_lerp(amt * 2, 230, 170)
            )
        } else {
            intArrayOf(
                math.color_lerp((amt - 0.5f) * 2, 239, 111),
                math.color_lerp((amt - 0.5f) * 2, 218, 228),
                math.color_lerp((amt - 0.5f) * 2, 170, 211)
            )
        }
        return MathHelper.packRgb(r / 255f, g/255f, b/255f)
    }

    //  Item stuff
    //intellij automatically selected List<Text> instead of MutableList<Text> and it took me like 2 hours to find the issue
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val maxMedia = getMaxMedia(stack)
        if (maxMedia > 0) {
            val media = getMedia(stack)
            val fullness = getMediaFullness(stack)
            val color = TextColor.fromRgb(getItemBarColor(stack))

            val mediaAmount = Text.literal(DUST_AMOUNT.format(media.toFloat() / MediaConstants.DUST_UNIT))
            val percentFull = Text.literal(PERCENTAGE.format(100 * fullness) + "%")
            val maxCapacity = Text.translatable("hexcasting.tooltip.media", DUST_AMOUNT.format(maxMedia / MediaConstants.DUST_UNIT))

            mediaAmount.styledWith { style -> style.withColor(HEX_COLOR) }
            maxCapacity.styledWith { style -> style.withColor(HEX_COLOR) }
            percentFull.styledWith { style -> style.withColor(color) }

            tooltip.add(Text.translatable("hexcasting.tooltip.media_amount.advanced", mediaAmount, maxCapacity, percentFull))
        }

        super.appendTooltip(stack, world, tooltip, context)
    }
}
