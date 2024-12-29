package io.github.candycalc.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import io.github.candycalc.Phlexiful;

@Mod(Phlexiful.MOD_ID)
public final class ExampleModForge {
    public ExampleModForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Phlexiful.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        Phlexiful.init();
    }
}
