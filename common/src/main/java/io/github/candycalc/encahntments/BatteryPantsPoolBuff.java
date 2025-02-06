package io.github.candycalc.encahntments;

import io.github.candycalc.registry.ItemRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BatteryPantsPoolBuff extends Enchantment {
    public BatteryPantsPoolBuff(Rarity rarity, EnchantmentCategory enchantmentCategory, EquipmentSlot... equipmentSlots) {
        super(rarity, enchantmentCategory, equipmentSlots);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.is(ItemRegistry.BATTERY_PANTS.get());
    }
}
