package io.github.candycalc.util

import at.petrak.hexcasting.client.render.PatternColors
import at.petrak.hexcasting.client.render.PatternSettings
import net.minecraft.util.math.ColorHelper

class PatternRenderHelpers {
    //  whoever chose this way of storing colors is my nemesis.

    companion object {
        private val color: Int = ColorHelper.Argb.getArgb(255, 111, 228, 211)
        private val color2: Int = ColorHelper.Argb.getArgb(255, (111 * .75).toInt(), (228 * .75).toInt(), (211 * .75).toInt())

        val INVENTORY_SCRY_COLOR: PatternColors = PatternColors(color, color2)

        private val STILL: PatternSettings.ZappySettings = PatternSettings.ZappySettings(1, 0f, 0f, 0f, PatternSettings.ZappySettings.READABLE_OFFSET, PatternSettings.ZappySettings.READABLE_SEGMENT)

        val INVENTORY_SCRY_SETTINGS: PatternSettings = PatternSettings(
            "inventory_scry",
            PatternSettings.PositionSettings.paddedSquare(2.0 / 16),
            PatternSettings.StrokeSettings.fromStroke(0.8 / 16),
            STILL
        )
    }
}