package io.github.candycalc.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.candycalc.encahntments.BatteryPantsPoolBuff;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Supplier;

import static io.github.candycalc.Phlexiful.MOD_ID;

public class EnchantRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(MOD_ID, Registries.ENCHANTMENT);

    public static final RegistrySupplier<Enchantment> BATTER_PANTS_POOL_BUFF = addToRegistry("battery_pants_pool_buff", () -> new BatteryPantsPoolBuff(Enchantment.Rarity.COMMON, EnchantmentCategory.ARMOR_LEGS, EquipmentSlot.LEGS));

    private static <T extends Enchantment> RegistrySupplier<T> addToRegistry(String name, Supplier<T> enchantment) {
        return ENCHANTMENTS.register(name, enchantment);
    }

    public static void register() {
        ENCHANTMENTS.register();
    }
}
