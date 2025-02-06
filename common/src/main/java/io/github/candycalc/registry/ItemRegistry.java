package io.github.candycalc.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.candycalc.items.WeldingMask;
import io.github.candycalc.items.armor.BatteryPants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

import static io.github.candycalc.Phlexiful.MOD_ID;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(MOD_ID, Registries.CREATIVE_MODE_TAB);



    public static final RegistrySupplier<Item> BATTERY_PANTS = addToRegistry("battery_pants", () -> new BatteryPants(ArmorItem.Type.LEGGINGS, defaultProperties()));
    public static final RegistrySupplier<Item> WELDING_MASK = addToRegistry("welding_mask", () -> new WeldingMask(defaultProperties()));
    public static final RegistrySupplier<Item> MEDIA_WEAVE = addToRegistry("media_weave", () -> new Item(defaultProperties()));


    public static final RegistrySupplier<CreativeModeTab> PHLEXIFUL_TAB = TABS.register(
            "phlexiful_tab",
            () -> CreativeTabRegistry.create(
                    Component.translatable("category.phlexiful_tab"),
                    () -> new ItemStack(WELDING_MASK.get().asItem())
            )
    );

    public static Item.Properties defaultProperties() {
        return new Item.Properties().arch$tab(PHLEXIFUL_TAB);
    }

    private static <T extends Item> RegistrySupplier<T> addToRegistry(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static void register() {
        ITEMS.register();
        TABS.register();
    }
}
