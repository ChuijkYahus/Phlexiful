package io.github.candycalc.casting.spell.caddisfly

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadOffhandItem
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.utils.hasCompound
import at.petrak.hexcasting.api.utils.remove
import io.github.candycalc.util.PhlexUtil
import net.minecraft.item.ItemStack

class OpClearCaddisfly : SpellAction {
    override val argc = 0


    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val (handStack, hand) = env.getHeldItemToOperateOn { it.hasCompound(PhlexUtil.CADDISFLY_TAG) } ?: throw MishapBadOffhandItem.of(
            ItemStack.EMPTY.copy(), "caddisflied")

        return SpellAction.Result(
            Spell(handStack),
            MediaConstants.DUST_UNIT,
            listOf()
        )
    }

    private data class Spell(val stack: ItemStack): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            stack.remove(PhlexUtil.CADDISFLY_TAG)
        }
    }
}