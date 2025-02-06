package io.github.candycalc.casting.actions.armor

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.asActionResult;
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.misc.MediaConstants
import io.github.candycalc.casting.mishaps.MishapBadArmorItem
import io.github.candycalc.items.armor.BatteryPants
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem

object OpReadPantsMedia : ConstMediaAction {
    override val argc = 0

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val caster = env.castingEntity
        if (caster !is ServerPlayer)
            return listOf(NullIota())

        val itemStack = caster.getItemBySlot(EquipmentSlot.LEGS)

        if (itemStack.isEmpty || itemStack.item !is BatteryPants)
            throw MishapBadArmorItem.of(itemStack, ArmorItem.Type.LEGGINGS, "iota.read")

        val datum = ((itemStack.item as BatteryPants).getMedia(itemStack) / MediaConstants.DUST_UNIT).asActionResult

        return datum
    }
}