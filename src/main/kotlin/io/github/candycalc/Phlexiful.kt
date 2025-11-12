package io.github.candycalc

import io.github.candycalc.registry.BlockRegistry
import io.github.candycalc.registry.CreativeGroupRegistry
import io.github.candycalc.registry.ItemRegistry
import io.github.candycalc.registry.PatternRegistry
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Phlexiful : ModInitializer {
    val logger = LoggerFactory.getLogger("phlexiful")
	const val MOD_ID = "phlexiful"

	override fun onInitialize() {
		PatternRegistry.register()
		BlockRegistry.register()
		ItemRegistry.register()
		CreativeGroupRegistry.register()
	}
}