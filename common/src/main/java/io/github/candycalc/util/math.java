package io.github.candycalc.util;

import net.minecraft.util.Mth;

public class math {
    // Bottosson's linear sRGB blend as per Skye's recommendation
    public static int color_lerp(float m, int x, int y) {
        return (int) (255 * sRGB(Mth.lerp(m, inv_sRGB((float) x / 255), inv_sRGB((float) y / 255))));
    }

    public static double sRGB(double x) {
        if (x >= 0.0031308) {
            return ((1.055) * Math.pow(x, 1.0 / 2.4) - 0.055);
        } else {
            return 12.92 * x;
        }
    }

    public static double inv_sRGB(double x) {
        if (x >= 0.04045)
            return Math.pow((x + 0.055)/(1 + 0.055), 2.4);
        else
            return x / 12.92;
    }
}
