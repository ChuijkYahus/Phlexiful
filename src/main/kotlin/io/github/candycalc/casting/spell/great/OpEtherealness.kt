package io.github.candycalc.casting.spell.great

import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadCaster
import io.github.candycalc.util.EntityDataSaver
import io.github.candycalc.util.EtherealnessData
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity

class OpEtherealness : SpellAction {
    override val argc: Int = 0

    override fun execute(
        args: List<Iota>,
        env: CastingEnvironment
    ): SpellAction.Result {
        val caster: LivingEntity? = env.castingEntity
        caster?.isPlayer ?: throw MishapBadCaster()

        return SpellAction.Result(
            Spell(caster as PlayerEntity),
            0,
            listOf()
        )
    }


    private data class Spell(val caster: PlayerEntity): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            //caster.abilities.allowFlying = true
            caster.abilities.flying = true
            //EtherealnessData.setEthereal(caster as EntityDataSaver, true) //noclip is reassigned every tick in the player entity class. Need to mixin it.
            //caster.isOnGround = false
            caster.sendAbilitiesUpdate()
        }
    }
}
