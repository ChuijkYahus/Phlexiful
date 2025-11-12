package io.github.candycalc.mixin;

import io.github.candycalc.Phlexiful;
import io.github.candycalc.registry.ItemRegistry;
import io.github.candycalc.util.ItemStackAccessor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

//  do not talk to me about this mixin unless it is causing issues
//  I don't need to relive this unnecessarily
@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackAccessor {
    //@Shadow @Nullable public abstract NbtCompound getNbt();
//
    //@Shadow public abstract NbtCompound getOrCreateNbt();
//
    //@Shadow public abstract Item getItem();
//
    //@Shadow public abstract String toString();
//
    //@Shadow public abstract boolean isOf(Item item);
//
    //@Unique @Nullable
    //private Long phlexiful$media;
//
    //@Override
    //public @Nullable Long phlexiful$getMedia() {
    //    return Objects.requireNonNullElseGet(phlexiful$media, () -> this.getOrCreateNbt().getLong("hexcasting:media"));
    //}
//
    //@Override
    //public void phlexiful$setMedia(Long dust) {
    //    phlexiful$media = dust;
    //}
//
//
    //@Inject(method = "getNbt", at = @At("RETURN"), cancellable = true)
    //private void injectGetNbt(CallbackInfoReturnable<NbtCompound> cir) {
    //    if (phlexiful$media != null && this.isOf(ItemRegistry.Companion.getBATTERY_PANTS())) {
    //        NbtCompound out = cir.getReturnValue(); //as long as it works I don't care. Please say it works
    //        out.remove("hexcasting:media");
    //        out.putLong("hexcasting:media", phlexiful$media);
    //        cir.setReturnValue(out);
    //    }
    //}
}
