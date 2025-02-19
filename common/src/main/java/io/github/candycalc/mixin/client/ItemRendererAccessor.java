package io.github.candycalc.mixin.client;

import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


// I genuienly do not know what this means at all
@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {
    @Accessor(value = "itemModelShaper")
    ItemModelShaper mccourse$getModelShaper();
}
