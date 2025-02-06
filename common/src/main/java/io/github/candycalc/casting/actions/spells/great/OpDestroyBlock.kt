package io.github.candycalc.patterns.great

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.mod.HexConfig
import at.petrak.hexcasting.api.mod.HexTags
import at.petrak.hexcasting.xplat.IXplatAbstractions
import io.github.candycalc.Phlexiful
import io.github.candycalc.registry.BlockRegistry
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementList
import net.minecraft.commands.Commands
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.commands.AdvancementCommands
import net.minecraft.world.level.block.Blocks
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.Mth
import net.minecraft.world.item.Tiers
import net.minecraft.world.phys.Vec3
import kotlin.math.sign

object OpDestroyBlock : SpellAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val vecPos = args.getVec3(0, argc)
        val pos = BlockPos.containing(vecPos)
        val blockState = env.world.getBlockState(pos)
        env.assertPosInRangeForEditing(pos)

        val isCheap = blockState.`is`(HexTags.Blocks.CHEAP_TO_BREAK_BLOCK)

        val isUnbreakable = blockState.block.defaultDestroyTime().sign < 0

        val cost = if (isUnbreakable) 10f else Mth.clamp(blockState.block.defaultDestroyTime() - 2.0f, 0f, 48f) / 48L + 0.01

        return SpellAction.Result(
            Spell(pos),
            if (isCheap) 0 else cost.toLong() * MediaConstants.DUST_UNIT, // price should be based off hardness. <=stone is free, >stone gradually increases
            listOf(ParticleSpray.burst(Vec3.atCenterOf(pos), 1.0))
        )
    }

    private data class Spell(val pos: BlockPos): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            val blockstate = env.world.getBlockState(pos)
            //val tier = HexConfig.server().opBreakHarvestLevel()//TODO("make this use phlexiful config instead of hex")

            if (blockstate.`is`(Blocks.BEDROCK)) { // spectrum dragonbone?? This should be data controlled, like a recipe.
                env.world.setBlock(pos, BlockRegistry.COBBLED_BEDROCK.get().defaultBlockState(), 3)
                //TODO("Seperate destroy method and give it custom sounds")
                val sound: SoundEvent = SoundEvents.GENERIC_EXPLODE
                env.world.playSound(null, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), sound, SoundSource.BLOCKS, 1f, 1f);
                //val advan =
                (env.castingEntity as ServerPlayer).advancements.award(env.world.server.advancements.getAdvancement(ResourceLocation(Phlexiful.MOD_ID, "destroy_bedrock")), "")
            } else if (
                !blockstate.isAir &&
                blockstate.getDestroySpeed(env.world, pos) >= 0f &&
                IXplatAbstractions.INSTANCE.isCorrectTierForDrops(Tiers.NETHERITE, blockstate) &&
                IXplatAbstractions.INSTANCE.isBreakingAllowed(env.world, pos, blockstate, env.castingEntity as? ServerPlayer)
                ) {
                env.world.destroyBlock(pos, false, env.castingEntity)
            }
        }
    }
}