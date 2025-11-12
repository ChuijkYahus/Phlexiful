package io.github.candycalc.casting.spell.caddisfly

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import net.minecraft.item.ItemStack
import at.petrak.hexcasting.api.casting.asActionResult

class OpReadCaddisfly : SpellAction {
    override val argc: Int = 1

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        args.getEntity(0, argc).type
        return SpellAction.Result(
            Spell(0),
            0,
            listOf()
        )
    }

    private data class Spell(val item: Int): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            item
        }
    }
}