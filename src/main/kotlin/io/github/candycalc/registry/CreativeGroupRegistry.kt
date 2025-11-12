package io.github.candycalc.registry

import io.github.candycalc.Phlexiful
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.function.Supplier

open class CreativeGroupRegistry {
    companion object {
        val PHLEX: ItemGroup = Registry.register(Registries.ITEM_GROUP,
            Identifier(Phlexiful.MOD_ID, "phlex"),
            FabricItemGroup.builder()
                .displayName(Text.translatable("itemgroup.phlex"))
                .icon(Supplier{ ItemRegistry.WELDING_MASK.defaultStack })
                .entries { displayContext, entries ->
                    //  Items
                    //entries.add { ItemRegistry.BATTERY_PANTS }
                    entries.add { ItemRegistry.WELDING_MASK }

                    //  Blocks
                    entries.add { BlockRegistry.COBBLED_BEDROCK.asItem() }
                }
                .build())

        open fun register() {
            Phlexiful.logger.info("registering " + Phlexiful.MOD_ID + "'s creative tabs")
        }
    }
}