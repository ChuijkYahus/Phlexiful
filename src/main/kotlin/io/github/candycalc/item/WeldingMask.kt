package io.github.candycalc.items

import at.petrak.hexcasting.common.items.HexBaubleItem
import at.petrak.hexcasting.common.items.ItemLens
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorItem

class WeldingMask(pProperties: FabricItemSettings) : ItemLens(pProperties), HexBaubleItem {
    val equipmentSlot: EquipmentSlot
        get() = ArmorItem.Type.HELMET.equipmentSlot
}