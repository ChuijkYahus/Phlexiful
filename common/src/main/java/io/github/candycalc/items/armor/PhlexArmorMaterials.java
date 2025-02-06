package io.github.candycalc.items.armor;

import io.github.candycalc.Phlexiful;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum PhlexArmorMaterials implements ArmorMaterial {
    BATTERY("battery", 0, new int[]{1, 1, 1, 1}, 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, null)
    ;

    private final String name;
    private final Integer durabilityMultiplier;
    private final int[] protectionAmounts;
    private final Integer enchantability;
    private  final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngrediant;

    private static final int[] BASE_DURABILITY = {11, 16, 15, 13};

    PhlexArmorMaterials(String name, Integer durabilityMultiplier, int[] protectionAmounts, Integer enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngrediant) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngrediant = repairIngrediant;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngrediant.get();
    }

    @Override
    public @NotNull String getName() {
        return Phlexiful.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
