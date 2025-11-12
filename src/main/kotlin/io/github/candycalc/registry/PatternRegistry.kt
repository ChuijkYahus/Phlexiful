package io.github.candycalc.registry

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.hex.HexActions
import io.github.candycalc.Phlexiful
import io.github.candycalc.casting.spell.caddisfly.OpCaddisfly
import io.github.candycalc.casting.spell.caddisfly.OpClearCaddisfly
import io.github.candycalc.casting.spell.great.OpDestroyBlock
import io.github.candycalc.casting.spell.great.OpEtherealness
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

open class PatternRegistry {
    companion object {
        var HASHMAP: MutableMap<Identifier?, ActionRegistryEntry?> =
            HashMap<Identifier?, ActionRegistryEntry?>()

        val DESTROY_BLOCK: ActionRegistryEntry = wrap("destroy_block", HexPattern.fromAngles("qaqqqqqwaeaea", HexDir.EAST),
            OpDestroyBlock
        )
        //val ETHEREALNESS: ActionRegistryEntry = wrap("etherealness", HexPattern.fromAngles("dqawa", HexDir.EAST),
        //    OpEtherealness())
        //DISSOCIATE_EVAL = wrap("dissociate_eval", HexPattern.fromAngles("dwddwde", HexDir.EAST), OpDissociateEval.INSTANCE),
        //val SETPANTSTHRESHHOLD: ActionRegistryEntry = wrap(
        //    "set_pants_threshhold",
        //    HexPattern.fromAngles("aedwd", HexDir.NORTH_WEST),
        //    OpSetPantsThreshhold)
        //val GETPANTSTHRESHHOLD: ActionRegistryEntry = wrap(
        //    "get_pants_threshhold", HexPattern.fromAngles("dqawa", HexDir.NORTH_EAST),
        //    OpReadPantsThreshhold)
        //val READPANTSMEDIA: ActionRegistryEntry = wrap(
        //    "read_pants_media",
        //    HexPattern.fromAngles("aeawaw", HexDir.SOUTH_EAST),
        //    OpReadPantsMedia)

        val CADDISFLY: ActionRegistryEntry = wrap(
            "caddisfly",
            HexPattern.fromAngles("wwaadadaddwwaadadaddww", HexDir.EAST),
            OpCaddisfly())
        val CLEAR_CADDISFLY: ActionRegistryEntry = wrap(
            "clear_caddisfly",
            HexPattern.fromAngles("wwaqwwedww", HexDir.EAST),
            OpClearCaddisfly())
        //  READ_CADDISFLY is in hexal compat

        //  HEXAL COMPAT
        //val READ_CADDISFLY = if (FabricLoader.getInstance().isModLoaded(PhlexInterop.HEXAL_ID)) {
        //    wrap(
        //        "read_caddisfly",
        //        HexPattern.fromAngles("wwaadadaddwwaqww", HexDir.EAST),
        //        OpReadCaddisfly())
        //} else { null }


        fun wrap(name: String?, pattern: HexPattern?, action: Action?): ActionRegistryEntry {
            val key: Identifier = Identifier(Phlexiful.MOD_ID, name)
            val `val` = ActionRegistryEntry(pattern, action)
            HASHMAP.put(key, `val`)
            return `val`
        }

        fun register() {
            val reg = HexActions.REGISTRY
            for (pair in HASHMAP.entries) {
                Registry.register(reg, pair.key, pair.value)
            }
        }
    }
}