package io.github.candycalc.items.armor;

import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.api.item.MediaHolderItem;
import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.api.utils.MathUtils;
import at.petrak.hexcasting.api.utils.MediaHelper;
import at.petrak.hexcasting.api.utils.NBTHelper;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.github.candycalc.Phlexiful;
import io.github.candycalc.encahntments.BatteryPantsPoolBuff;
import io.github.candycalc.util.math;
import io.github.candycalc.registry.EnchantRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class BatteryPants extends ArmorItem implements MediaHolderItem, IotaHolderItem {
    public BatteryPants(Type type, Properties properties) {
        super(PhlexArmorMaterials.BATTERY, type, properties);
    }

    public static final String TAG_MEDIA = "hexcasting:media";
    public static final String TAG_MAX_MEDIA = "hexcasting:start_media";
    public static final String TAG_DATA = "data";
    public static final String TAG_WORN = "phlexical:is_worn"; //I don't want to talk about how cursed this is (unless you know a better way pls)
    public static final TextColor HEX_COLOR = TextColor.fromRgb(0xb38ef3);
    private static final DecimalFormat PERCENTAGE = new DecimalFormat("#####");
    //removed trimming of trailing 0s because it was distracting with the constant increase.
    private static final DecimalFormat DUST_AMOUNT = new DecimalFormat("###,##0.00");

    static {
        PERCENTAGE.setRoundingMode(RoundingMode.DOWN);
    }


    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl) {
        if (!NBTHelper.hasLong(itemStack, TAG_MEDIA)) {
            setMedia(itemStack, 0);
        }

        if (!NBTHelper.hasLong(itemStack, TAG_MAX_MEDIA)) {
            setMaxMedia(itemStack, MediaConstants.DUST_UNIT * 5);
        }

        // if it is in the leggings slot, increment the battery
        if (((Player) entity).getItemBySlot(EquipmentSlot.LEGS) == itemStack) {
            long media = getMedia(itemStack);
            long maxMedia = getMaxMedia(itemStack);
            // crimew not make borderline unintelligible code with magic numbers challenge (impossible)
            // slowly decreases rate of media generation. Starts at 1/200 dust, ends at 1/600 dust per tick
            setMedia(itemStack, media + (long) (0.05 * Math.pow(2, -0.79248 * (double) media / maxMedia) / 10 * MediaConstants.DUST_UNIT));
            if (!NBTHelper.getBoolean(itemStack, TAG_WORN)) {
                NBTHelper.putBoolean(itemStack, TAG_WORN, true);
            }
        } else {
            if (NBTHelper.getBoolean(itemStack, TAG_WORN)) {
                NBTHelper.putBoolean(itemStack, TAG_WORN, false);
            }
        }
    }

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
        NBTHelper.putLong(stack, TAG_MEDIA, MathUtils.clamp(media, 0, getMaxMedia(stack) + MediaConstants.DUST_UNIT * EnchantmentHelper.getItemEnchantmentLevel(EnchantRegistry.BATTER_PANTS_POOL_BUFF.get(), stack)));
    }

    public void setMaxMedia(ItemStack stack, long media) {
        NBTHelper.putLong(stack, TAG_MAX_MEDIA, media);
    }

    @Override
    public boolean canProvideMedia(ItemStack itemStack) {
        return NBTHelper.getBoolean(itemStack, TAG_WORN);
    }

    @Override
    public boolean canRecharge(ItemStack itemStack) {
        return false;
    }

    @Override
    public int getConsumptionPriority(ItemStack stack) {
        return 9999; //one less than Bottomless Phial from beholderface mod
    }

    @Override
    public long withdrawMedia(ItemStack itemStack, long cost, boolean simulate) {
        Iota pantsIota = IXplatAbstractions.INSTANCE.findDataHolder(itemStack).readIota(null);
        double threshhold = pantsIota == null ? -1 : ((DoubleIota) pantsIota).getDouble();
        if (cost <= threshhold * MediaConstants.DUST_UNIT || threshhold < 0) {
            return MediaHolderItem.super.withdrawMedia(itemStack, cost, simulate);
        } else {
            return 0;
        }
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack) {
        return getMedia(itemStack) > 0;
    }


    @Override
    public int getBarColor(ItemStack pStack) {
        long media = getMedia(pStack);
        long maxMedia = getMaxMedia(pStack);
        if (media <= maxMedia) {
            return MediaHelper.mediaBarColor(media, maxMedia);
        }
        float amt = ((float) media / maxMedia) - 1;
        int r;
        int g;
        int b;
        if (amt <= 0.5) {
            r = math.color_lerp(amt*2, 254, 239);
            g = math.color_lerp(amt*2, 203, 218);
            b = math.color_lerp(amt*2, 230, 170);
        } else {
            r = math.color_lerp((amt-0.5f)*2, 239, 111);
            g = math.color_lerp((amt-0.5f)*2, 218, 228);
            b = math.color_lerp((amt-0.5f)*2, 170, 211);
        }
        return Mth.color(r / 255f, g / 255f, b / 255f);
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

            var color = TextColor.fromRgb(getBarColor(pStack));

            var mediamount = Component.literal(DUST_AMOUNT.format(media / (float) MediaConstants.DUST_UNIT));
            var percentFull = Component.literal(PERCENTAGE.format(100f * fullness) + "%");
            var maxCapacity = Component.translatable("hexcasting.tooltip.media", DUST_AMOUNT.format(maxMedia / (float) MediaConstants.DUST_UNIT));

            mediamount.withStyle(style -> style.withColor(HEX_COLOR));
            maxCapacity.withStyle(style -> style.withColor(HEX_COLOR));
            percentFull.withStyle(style -> style.withColor(color));

            pTooltipComponents.add(
                    Component.translatable("hexcasting.tooltip.media_amount.advanced",
                            mediamount, maxCapacity, percentFull));

            IotaHolderItem.appendHoverText(this, pStack, pTooltipComponents, pIsAdvanced);
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return true;
    }

    @Override
    public @Nullable CompoundTag readIotaTag(ItemStack itemStack) {
        return NBTHelper.getCompound(itemStack, TAG_DATA);
    }

    @Override
    public boolean writeable(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean canWrite(ItemStack itemStack, @Nullable Iota iota) {
        return false;
    }

    @Override
    public void writeDatum(ItemStack itemStack, @Nullable Iota iota) {
        if (iota == null) {
            itemStack.removeTagKey(TAG_DATA);
        } else {
            NBTHelper.put(itemStack, TAG_DATA, IotaType.serialize(iota));
        }
    }
}
