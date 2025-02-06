package io.github.candycalc;

import io.github.candycalc.registry.BlockRegistry;
import io.github.candycalc.registry.EnchantRegistry;
import io.github.candycalc.registry.ItemRegistry;
import io.github.candycalc.registry.PatternRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Phlexiful {
    public static final String MOD_ID = "phlexiful";
    public static final Logger LOGGER = LoggerFactory.getLogger("Phlexiful");
    public static void log(String message) {
        LOGGER.info(message);
    }

    public static void init() {
        PatternRegistry.register();
        BlockRegistry.register();
        ItemRegistry.register();
        EnchantRegistry.register();
    }
}
