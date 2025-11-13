package io.github.candycalc.registry

import at.petrak.hexcasting.common.items.ItemLens
import at.petrak.hexcasting.xplat.IXplatAbstractions
import io.github.candycalc.Phlexiful
import io.github.candycalc.item.armor.BatteryPants
import io.github.candycalc.items.WeldingMask
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

open class ItemRegistry {
    companion object {

        val WELDING_MASK: Item = registerItem("welding_mask", WeldingMask(
            IXplatAbstractions.INSTANCE.addEquipSlotFabric(EquipmentSlot.HEAD)
                .maxCount(1) as FabricItemSettings
        ))
        //val HEXBLADE_TRIDENT: Item = registerItem("hexblade_trident", HexbladeTrident()

        //  block items are registered alongside blocks in BlockRegistry

        private fun registerItem(name: String, item: Item): Item {
            return Registry.register(
                Registries.ITEM,
                Identifier(Phlexiful.MOD_ID, name),
                item
            )
        }

        fun register() {
            Phlexiful.logger.info("registering " + Phlexiful.MOD_ID + "'s items")
        }
    }
}