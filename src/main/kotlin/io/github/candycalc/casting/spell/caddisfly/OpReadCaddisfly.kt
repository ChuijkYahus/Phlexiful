package io.github.candycalc.casting.spell.caddisfly

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.minecraft.item.ItemStack
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.mishaps.MishapBadOffhandItem
import at.petrak.hexcasting.api.utils.hasCompound
import io.github.candycalc.util.PhlexUtil
import miyucomics.hexpose.iotas.asActionResult
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.registry.Registries

class OpReadCaddisfly : ConstMediaAction {
    override val argc: Int = 0

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): List<Iota> {
        val (handStack, hand) = env.getHeldItemToOperateOn { !it.isOf(Items.AIR) } ?: throw MishapBadOffhandItem.of(
            ItemStack.EMPTY.copy(), "caddisflyable"
        )

        if (handStack.orCreateNbt.hasCompound(PhlexUtil.CADDISFLY_TAG)) {
            return ItemStack.fromNbt(handStack.orCreateNbt.getCompound(PhlexUtil.CADDISFLY_TAG)).asActionResult
        } else {
            throw MishapBadOffhandItem.of(ItemStack.EMPTY.copy(), "caddisflyable")
        }
    }
}