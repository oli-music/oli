package com.oli.music.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Warm Analog shape scale — deliberately grounded rather than maximally
 * rounded. Vinyl/chill visual identity reads as "soft-edged but solid,"
 * not the bubbly full-pill Material You expressive look, so corner radii
 * here are moderate across the scale rather than approaching 50%.
 */
val OliShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp),
)
