package io.github.candycalc.items;

import at.petrak.hexcasting.common.items.ItemLens;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Equipable;
import org.jetbrains.annotations.NotNull;

public class WeldingMask extends ItemLens implements Equipable {
    public WeldingMask(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return ArmorItem.Type.HELMET.getSlot();
    }
}
