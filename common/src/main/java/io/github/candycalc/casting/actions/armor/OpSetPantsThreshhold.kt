package io.github.candycalc.casting.actions.armor

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.Iota
import io.github.candycalc.casting.mishaps.MishapBadArmorItem
import io.github.candycalc.items.armor.BatteryPants
import io.github.candycalc.registry.ItemRegistry
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem


object OpSetPantsThreshhold : ConstMediaAction {
    override val argc = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        args.getDouble(0, argc)
        val caster = env.castingEntity
        if (caster !is ServerPlayer)
            return emptyList()

        val itemStack = caster.getItemBySlot(EquipmentSlot.LEGS)

        if (!itemStack.`is`(ItemRegistry.BATTERY_PANTS.get())) {
            throw MishapBadArmorItem.of(itemStack, ArmorItem.Type.LEGGINGS, "iota.write", ItemRegistry.BATTERY_PANTS.get())
        }

        (itemStack.item as BatteryPants).writeDatum(itemStack, args[0])

        return emptyList()
    }
}