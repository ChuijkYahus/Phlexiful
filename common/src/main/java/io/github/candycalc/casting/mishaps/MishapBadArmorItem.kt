package io.github.candycalc.casting.mishaps

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.Mishap
import at.petrak.hexcasting.api.pigment.FrozenPigment
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack

class MishapBadArmorItem(val item: ItemStack?, val wanted: Component, val slot: ArmorItem.Type) : Mishap() {
    override fun accentColor(ctx: CastingEnvironment, errorCtx: Context): FrozenPigment =
        dyeColor(DyeColor.BROWN)

    override fun errorMessage(ctx: CastingEnvironment, errorCtx: Context)  = if (item?.isEmpty == false)
        error("bad_item.armor", wanted, slot.getName(), item.displayName)
    else
        error("no_item.armor", wanted, slot.getName())

    override fun execute(env: CastingEnvironment, errorCtx: Context, stack: MutableList<Iota>) {
        // Should this drop the armor? Unless curse of binding ofc
    }

    companion object {
        @JvmStatic
        fun of(item: ItemStack?, slot: ArmorItem.Type, stub: String, vararg args: Any): MishapBadArmorItem {
            return MishapBadArmorItem(item, "hexcasting.mishap.bad_item.$stub".asTranslatedComponent(*args), slot)
        }
    }
}