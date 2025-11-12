package io.github.candycalc.util

import net.minecraft.util.math.MathHelper
import kotlin.math.pow

class math {
    companion object {
        // Bottosson's linear sRGB blend as per Skye's recommendation
        fun color_lerp(m: Float, x: Int, y: Int): Int {
            return (255 * sRGB(
                MathHelper.lerp(
                    m,
                    inv_sRGB((x.toFloat() / 255)),
                    inv_sRGB((y.toFloat() / 255))
                )
            )).toInt()
        }

        fun sRGB(x: Float): Float {
            return if (x >= 0.0031308f) ((1.055f) * x.pow(1.0f / 2.4f) - 0.055f) else 12.92f * x
        }

        fun inv_sRGB(x: Float): Float {
            return if (x >= 0.04045f) ((x + 0.055f) / (1f + 0.055f)).pow(2.4f) else x / 12.92f
        }
    }
}