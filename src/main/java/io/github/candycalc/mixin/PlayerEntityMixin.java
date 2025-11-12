package io.github.candycalc.mixin;

import io.github.candycalc.Phlexiful;
import io.github.candycalc.util.EntityDataSaver;
import io.github.candycalc.util.EtherealnessData;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isSpectator()Z", shift = At.Shift.AFTER))
    protected void tickInject(CallbackInfo ci) {
        PlayerEntity _this = (PlayerEntity) (Object) this;
        Phlexiful.INSTANCE.getLogger().info(String.valueOf(EtherealnessData.Companion.getEthereal((EntityDataSaver) _this)));
        boolean isEthereal = EtherealnessData.Companion.getEthereal((EntityDataSaver) _this);
        _this.noClip = _this.noClip || isEthereal;
    }
}
