package io.github.candycalc.registry

import io.github.candycalc.Phlexiful
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

open class BlockRegistry {
    companion object {
        val COBBLED_BEDROCK: Block = registerBlock(
            "cobbled_bedrock",
            // strength is set to -2 because pistons will not move blocks with -1 strength
            // why is there not just a "pushable" block property? This is the most sold game ever btw.
            Block(FabricBlockSettings.copyOf(Blocks.BEDROCK).strength(-2.0f, 3600000.0f))
        )

        private fun registerBlock(name: String, block: Block): Block {
            registerBlockItem(name, block)
            return Registry.register(
                Registries.BLOCK,
                Identifier(Phlexiful.MOD_ID, name),
                block
            )
        }

        private fun registerBlockItem(name: String, block: Block): Item {
            return Registry.register(
                Registries.ITEM,
                Identifier(Phlexiful.MOD_ID, name),
                BlockItem(block, FabricItemSettings()) as Item
            )
        }

        fun register() {
            Phlexiful.logger.info("registering " + Phlexiful.MOD_ID + "'s blocks")
        }
    }
}