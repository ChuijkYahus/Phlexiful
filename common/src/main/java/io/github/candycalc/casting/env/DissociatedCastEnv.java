package io.github.candycalc.casting.env;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.eval.env.PlayerBasedCastEnv;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

// ironic that it's a player based cast environment
public abstract class DissociatedCastEnv extends PlayerBasedCastEnv {
    protected DissociatedCastEnv(ServerPlayer caster, InteractionHand castingHand) {
        super(caster, castingHand);
    }

    @Override
    public LivingEntity getCastingEntity() {
        return null;
    }
}
