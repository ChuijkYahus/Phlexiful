package io.github.candycalc.items

import at.petrak.hexcasting.common.items.HexBaubleItem
import at.petrak.hexcasting.common.items.ItemLens
import at.petrak.hexcasting.common.lib.HexAttributes
import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import dev.emi.trinkets.api.SlotReference
import dev.emi.trinkets.api.TrinketItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.item.ArmorItem
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import java.util.UUID

class WeldingMask(pProperties: FabricItemSettings) : TrinketItem(pProperties) {
    val equipmentSlot: EquipmentSlot
        get() = ArmorItem.Type.HELMET.equipmentSlot

    // our holy vanilla attribute system
    override fun getAttributeModifiers(slot: EquipmentSlot?): Multimap<EntityAttribute?, EntityAttributeModifier?>? {
        val out = HashMultimap.create<EntityAttribute, EntityAttributeModifier>()
        if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
            out.put(HexAttributes.GRID_ZOOM, ItemLens.GRID_ZOOM)
            out.put(HexAttributes.SCRY_SIGHT, ItemLens.SCRY_SIGHT)
        }
        return out
    }

    // their evil and confusing trinkets attribute system
    override fun getModifiers(stack: ItemStack, slot: SlotReference, entity: LivingEntity, uuid: UUID): Multimap<EntityAttribute, EntityAttributeModifier> {
        val out = super.getModifiers(stack, slot, entity, uuid)

        out.put(HexAttributes.GRID_ZOOM, ItemLens.GRID_ZOOM)
        out.put(HexAttributes.SCRY_SIGHT, ItemLens.SCRY_SIGHT)

        return out
    }
}