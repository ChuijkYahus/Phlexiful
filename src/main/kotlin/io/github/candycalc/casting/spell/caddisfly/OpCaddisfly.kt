package io.github.candycalc.casting.spell.caddisfly

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getItemEntity
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadOffhandItem
import at.petrak.hexcasting.api.misc.MediaConstants
import io.github.candycalc.util.PhlexUtil
import net.minecraft.entity.ItemEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound

class OpCaddisfly : SpellAction {
    override val argc: Int = 1

    override fun hasCastingSound(castingContext: CastingEnvironment): Boolean {
        return true
    }

    override fun awardsCastingStat(castingContext: CastingEnvironment): Boolean {
        return true
    }


    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val addend = args.getItemEntity(0, argc)
        val (handStack, hand) = env.getHeldItemToOperateOn { !it.isOf(Items.AIR) } ?: throw MishapBadOffhandItem.of(
            ItemStack.EMPTY.copy(), "caddisflyable")

        //check if target is in bounds
        env.assertEntityInRange(addend)

        return SpellAction.Result(
            Spell(handStack, addend),
            MediaConstants.CRYSTAL_UNIT,
            listOf(ParticleSpray.Companion.burst(addend.pos, 0.5, 10))
        )
    }

    private data class Spell(private val item: ItemStack, private val addend: ItemEntity) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            var caddisfly_nbt = addend.stack.writeNbt(NbtCompound())
            //no infinite nbt; chunkban spell comes out next update
            if (caddisfly_nbt.getCompound("tag").contains(PhlexUtil.CADDISFLY_TAG)) {
                caddisfly_nbt = caddisfly_nbt.getCompound("tag").getCompound(PhlexUtil.CADDISFLY_TAG)
            }
            item.getOrCreateNbt().put(PhlexUtil.CADDISFLY_TAG, caddisfly_nbt)
            //Phlexiful.logger.info(item.getOrCreateNbt().toString())
        }
    }
}