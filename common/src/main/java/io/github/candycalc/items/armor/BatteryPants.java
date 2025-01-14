package io.github.candycalc.items.armor;

import at.petrak.hexcasting.api.item.MediaHolderItem;
import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.api.utils.MathUtils;
import at.petrak.hexcasting.api.utils.MediaHelper;
import at.petrak.hexcasting.api.utils.NBTHelper;
import io.github.candycalc.Phlexiful;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import static io.github.candycalc.Phlexiful.log;

public class BatteryPants extends ArmorItem implements MediaHolderItem {
    public BatteryPants(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        if (!NBTHelper.hasLong(itemStack, TAG_MEDIA)) {
            setMedia(itemStack, 0);
        }

        if (!NBTHelper.hasLong(itemStack, TAG_MAX_MEDIA)) {
            setMaxMedia(itemStack, MediaConstants.DUST_UNIT);
        }

        // if there is room in the battery and it is in the leggings slot, increment the battery
        if (getMedia(itemStack) < getMaxMedia(itemStack) && ((Player) entity).getItemBySlot(EquipmentSlot.LEGS) == itemStack) {
            setMedia(itemStack, getMedia(itemStack) + MediaConstants.DUST_UNIT / 1200);
        }
    }

    public static final String TAG_MEDIA = "hexcasting:media";
    public static final String TAG_MAX_MEDIA = "hexcasting:start_media";

    public static final TextColor HEX_COLOR = TextColor.fromRgb(0xb38ef3);

    private static final DecimalFormat PERCENTAGE = new DecimalFormat("####");

    static {
        PERCENTAGE.setRoundingMode(RoundingMode.DOWN);
    }

    private static final DecimalFormat DUST_AMOUNT = new DecimalFormat("###,###.##");


    @Override
    public long getMedia(ItemStack stack) {
        return NBTHelper.getLong(stack, TAG_MEDIA);
    }

    @Override
    public long getMaxMedia(ItemStack stack) {
        return NBTHelper.getLong(stack, TAG_MAX_MEDIA);
    }

    @Override
    public void setMedia(ItemStack stack, long media) {
        NBTHelper.putLong(stack, TAG_MEDIA, MathUtils.clamp(media, 0, getMaxMedia(stack)));
    }

    public void setMaxMedia(ItemStack stack, long media) {
        NBTHelper.putLong(stack, TAG_MAX_MEDIA, MathUtils.clamp(media, 0, Long.MAX_VALUE));
    }

    @Override
    public boolean canProvideMedia(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean canRecharge(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        var media = getMedia(pStack);
        var maxMedia = getMaxMedia(pStack);
        return MediaHelper.mediaBarColor(media, maxMedia);
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        var media = getMedia(pStack);
        var maxMedia = getMaxMedia(pStack);
        return MediaHelper.mediaBarWidth(media, maxMedia);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        var maxMedia = getMaxMedia(pStack);
        if (maxMedia > 0) {
            var media = getMedia(pStack);
            var fullness = getMediaFullness(pStack);

            var color = TextColor.fromRgb(MediaHelper.mediaBarColor(media, maxMedia));

            var mediamount = Component.literal(DUST_AMOUNT.format(media / (float) MediaConstants.DUST_UNIT));
            var percentFull = Component.literal(PERCENTAGE.format(100f * fullness) + "%");
            var maxCapacity = Component.translatable("hexcasting.tooltip.media", DUST_AMOUNT.format(maxMedia / (float) MediaConstants.DUST_UNIT));

            mediamount.withStyle(style -> style.withColor(HEX_COLOR));
            maxCapacity.withStyle(style -> style.withColor(HEX_COLOR));
            percentFull.withStyle(style -> style.withColor(color));

            pTooltipComponents.add(
                    Component.translatable("hexcasting.tooltip.media_amount.advanced",
                            mediamount, maxCapacity, percentFull));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
