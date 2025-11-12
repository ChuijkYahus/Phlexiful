package io.github.candycalc.casting.spell.great

import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.mod.HexTags
import at.petrak.hexcasting.xplat.IXplatAbstractions
import io.github.candycalc.Phlexiful
import io.github.candycalc.registry.BlockRegistry
import net.minecraft.block.Blocks
import net.minecraft.item.ToolMaterials
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.util.Identifier
import kotlin.math.sign

object OpDestroyBlock : SpellAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val vecPos = args.getVec3(0, argc)
        val pos = BlockPos.ofFloored(vecPos)
        val blockState = env.world.getBlockState(pos)
        env.assertPosInRangeForEditing(pos)

        val isCheap = blockState.isIn(HexTags.Blocks.CHEAP_TO_BREAK_BLOCK)

        val isUnbreakable = blockState.block.hardness.sign < 0

        val cost = if (isUnbreakable) 10f else MathHelper.clamp(blockState.block.hardness - 2.0f, 0f, 48f) / 48L + 0.01

        return SpellAction.Result(
            Spell(pos),
            if (isCheap) 0 else cost.toLong() * MediaConstants.DUST_UNIT, // price should be based off hardness. <=stone is free, >stone gradually increases
            listOf(ParticleSpray.burst(Vec3d.ofCenter(pos), 1.0))
        )
    }

    private data class Spell(val pos: BlockPos): RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            val blockstate = env.world.getBlockState(pos)
            val tier = ToolMaterials.NETHERITE //HexConfig.server().opBreakHarvestLevel()//TODO("make this use phlexiful config instead of hex")

            if (blockstate.isOf(Blocks.BEDROCK)) { // spectrum dragonbone?? This should be data controlled, like a recipe.
                env.world.setBlockState(pos, BlockRegistry.COBBLED_BEDROCK.defaultState, 3)
                //TODO("Seperate destroy method and give it custom sounds")
                val sound: SoundEvent = SoundEvents.ENTITY_GENERIC_EXPLODE
                env.world.playSound(null, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), sound, SoundCategory.BLOCKS, 1f, 1f);

                (env.castingEntity as ServerPlayerEntity).advancementTracker.grantCriterion(env.world.server.advancementLoader.get(Identifier(Phlexiful.MOD_ID, "destroy_bedrock")), "")
            } else if (
                !blockstate.isAir &&
                blockstate.getHardness(env.world, pos) >= 0f &&
                IXplatAbstractions.INSTANCE.isCorrectTierForDrops(tier, blockstate) &&
                IXplatAbstractions.INSTANCE.isBreakingAllowed(env.world, pos, blockstate, env.castingEntity as? ServerPlayerEntity)
            ) {
                env.world.breakBlock(pos, false, env.castingEntity)
            }
        }
    }
}