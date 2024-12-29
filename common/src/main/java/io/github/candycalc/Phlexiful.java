package io.github.candycalc;

import io.github.candycalc.registry.BlockRegistry;
import io.github.candycalc.registry.PatternRegistry;

public final class Phlexiful {
    public static final String MOD_ID = "phlexiful";

    public static void init() {
        PatternRegistry.INSTANCE.register();
        BlockRegistry.register();
    }
}
