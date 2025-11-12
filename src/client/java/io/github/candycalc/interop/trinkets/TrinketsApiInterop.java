package io.github.candycalc.interop.trinkets;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.github.candycalc.registry.ItemRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class TrinketsApiInterop {
    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        TrinketRendererRegistry.registerRenderer(ItemRegistry.Companion.getWELDING_MASK(), new WeldingTrinketRenderer());
    }
}
