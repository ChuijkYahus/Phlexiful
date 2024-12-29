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
import io.github.candycalc.blocks.CobbledBedrock
import io.github.candycalc.registry.BlockRegistry
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Blocks
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.phys.Vec3

object OpDestroyBlock : SpellAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val vecPos = args.getVec3(0, argc)
        val pos = BlockPos.containing(vecPos)
        env.assertPosInRangeForEditing(pos)

        val isCheap = env.world.getBlockState(pos).`is`(HexTags.Blocks.CHEAP_TO_BREAK_BLOCK)

        return SpellAction.Result(
            Spell(pos),
            if (isCheap) 0 else MediaConstants.DUST_UNIT / 16, // price should be based off hardness. <=stone is free, >stone gradually increases
            listOf(ParticleSpray.burst(Vec3.atCenterOf(pos), 1.0))
        )
    }

    private data class Spell(val pos: BlockPos): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            val blockstate = env.world.getBlockState(pos)
            val tier = HexConfig.server().opBreakHarvestLevel()//TODO("make this use phlexiful config instead of hex")

            if (blockstate.`is`(Blocks.BEDROCK)) {
                env.world.setBlock(pos, BlockRegistry.COBBLED_BEDROCK.get().defaultBlockState(), 3)
                //TODO("Seperate destroy method and give it custom sounds")
            } else if (
                !blockstate.isAir &&
                blockstate.getDestroySpeed(env.world, pos) >= 0f &&
                IXplatAbstractions.INSTANCE.isCorrectTierForDrops(tier, blockstate) &&
                IXplatAbstractions.INSTANCE.isBreakingAllowed(env.world, pos, blockstate, env.castingEntity as? ServerPlayer)
                ) {
                env.world.destroyBlock(pos, false, env.castingEntity)
            }
        }
    }
}