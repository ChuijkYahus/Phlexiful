package io.github.candycalc.registry

import dev.architectury.registry.registries.DeferredRegister
import io.github.candycalc.Phlexiful.MOD_ID
import net.minecraft.core.registries.Registries

object ItemRegistry {
    private val REGISTRY = DeferredRegister.create(MOD_ID, Registries.ITEM)

    fun register() {
        REGISTRY.register()
    }
}