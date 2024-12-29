package io.github.candycalc.registry

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.HexRegistries
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier
import io.github.candycalc.Phlexiful.MOD_ID
import io.github.candycalc.patterns.great.OpDestroyBlock

object PatternRegistry {
    private val REGISTRY = DeferredRegister.create(MOD_ID, HexRegistries.ACTION)

    val DESTROY_BLOCK = addToRegistry("destroy_block", "a", HexDir.EAST, OpDestroyBlock, false)

    private fun addToRegistry(
        name: String,
        anglesig: String,
        dir: HexDir,
        action: Action,
        great: Boolean
    ): RegistrySupplier<ActionRegistryEntry> {
        return REGISTRY.register(name) { ActionRegistryEntry(HexPattern.fromAngles(anglesig, dir), action) }
    }

    fun register() {
        REGISTRY.register()
    }
}