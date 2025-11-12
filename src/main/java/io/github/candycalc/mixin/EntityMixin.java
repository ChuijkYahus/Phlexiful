package io.github.candycalc.mixin;

import io.github.candycalc.Phlexiful;
import io.github.candycalc.util.EntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
abstract class EntityMixin implements EntityDataSaver {
    @Unique
    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData() {
        if (this.persistentData == null) {
            this.persistentData = new NbtCompound();
        }
        return persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (persistentData != null) {
            nbt.put(Phlexiful.MOD_ID + ".data", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void InjectReadNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains(Phlexiful.MOD_ID + ".data", 10)) {
            persistentData = nbt.getCompound(Phlexiful.MOD_ID + ".data");
        }
    }
}
