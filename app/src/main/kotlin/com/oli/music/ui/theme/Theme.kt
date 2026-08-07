

package com.oli.music.ui.theme

import android.graphics.Bitmap
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.palette.graphics.Palette
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicColorScheme
import com.materialkolor.score.Score

/* =========================================================================
   Oli brand palette — "Warm Analog" (vinyl / chill vibes)
   ---------------------------------------------------------------------
   This is a hand-authored static palette, not algorithmically generated
   from a seed color, so these exact values are what actually ship rather
   than an approximation. Custom user-picked theme colors (below) still go
   through the algorithmic path, since there's no fixed palette to author
   for an arbitrary color the user chooses.
========================================================================= */
val WarmCharcoal = Color(0xFF181615)   // background
val DarkMocha = Color(0xFF24201E)      // surface
val BurntCoral = Color(0xFFFF6B5B)     // primary accent
val AmberGold = Color(0xFFFFAA00)      // secondary accent
val Cream = Color(0xFFF5F2EB)          // primary text
val MutedTaupe = Color(0xFFA8A099)     // secondary / muted text

val DefaultThemeColor = BurntCoral // seed used when the user's on the default Oli palette

private val WarmAnalogDarkColorScheme = darkColorScheme(
    primary = BurntCoral,
    onPrimary = WarmCharcoal,
    primaryContainer = Color(0xFF5C2A22),
    onPrimaryContainer = Cream,

    secondary = AmberGold,
    onSecondary = WarmCharcoal,
    secondaryContainer = Color(0xFF4D3A00),
    onSecondaryContainer = Cream,

    tertiary = Color(0xFFD97757), // soft terracotta — gives the palette a third note without competing with the primary accent
    onTertiary = WarmCharcoal,
    tertiaryContainer = Color(0xFF4A2E20),
    onTertiaryContainer = Cream,

    background = WarmCharcoal,
    onBackground = Cream,

    surface = DarkMocha,
    onSurface = Cream,
    surfaceVariant = Color(0xFF322C29),
    onSurfaceVariant = MutedTaupe,

    surfaceContainerLowest = Color(0xFF121110),
    surfaceContainerLow = Color(0xFF1D1A18),
    surfaceContainer = DarkMocha,
    surfaceContainerHigh = Color(0xFF2C2724),
    surfaceContainerHighest = Color(0xFF34302B),

    outline = Color(0xFF6B655D),
    outlineVariant = Color(0xFF3A3532),

    error = Color(0xFFFF6E5C),
    onError = WarmCharcoal,
    errorContainer = Color(0xFF5C2A22),
    onErrorContainer = Cream,

    inverseSurface = Cream,
    inverseOnSurface = WarmCharcoal,
    inversePrimary = Color(0xFFB33F31),

    scrim = Color(0xFF000000),
)

// Light-mode counterpart, keeping the same warm identity for users with
// dark theme switched off, rather than falling back to a generic light scheme.
private val WarmAnalogLightColorScheme = lightColorScheme(
    primary = Color(0xFFC9483A),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDAD2),
    onPrimaryContainer = Color(0xFF410A02),

    secondary = Color(0xFF9C6B00),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDDA8),
    onSecondaryContainer = Color(0xFF2E1F00),

    tertiary = Color(0xFFB3552F),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDBCB),
    onTertiaryContainer = Color(0xFF3A1300),

    background = Color(0xFFF7F3EC),
    onBackground = Color(0xFF221B18),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF221B18),
    surfaceVariant = Color(0xFFF0E4DD),
    onSurfaceVariant = Color(0xFF534741),

    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFBF2EB),
    surfaceContainer = Color(0xFFF5ECE4),
    surfaceContainerHigh = Color(0xFFEFE6DE),
    surfaceContainerHighest = Color(0xFFE9E0D8),

    outline = Color(0xFF857770),
    outlineVariant = Color(0xFFD7C7BE),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    inverseSurface = Color(0xFF382F2C),
    inverseOnSurface = Color(0xFFFEEEE7),
    inversePrimary = Color(0xFFFFB4A6),

    scrim = Color(0xFF000000),
)

@Composable
fun oliTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    pureBlack: Boolean = false,
    themeColor: Color = DefaultThemeColor,
    content: @Composable () -> Unit,
) {
    // The Oli default is our own hand-authored Warm Analog palette, not the
    // device's wallpaper-derived Material You color — that gave every user
    // a different, brand-inconsistent look by default. Custom user-picked
    // colors still generate an algorithmic Material3 palette from that seed.
    val baseColorScheme = if (themeColor == DefaultThemeColor) {
        if (darkTheme) WarmAnalogDarkColorScheme else WarmAnalogLightColorScheme
    } else {
        rememberDynamicColorScheme(
            seedColor = themeColor,
            isDark = darkTheme,
            specVersion = ColorSpec.SpecVersion.SPEC_2025,
            style = PaletteStyle.TonalSpot
        )
    }

    val colorScheme = remember(baseColorScheme, pureBlack, darkTheme) {
        if (darkTheme && pureBlack) {
            baseColorScheme.pureBlack(true)
        } else {
            baseColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = OliShapes,
        content = content
    )
}

fun Bitmap.extractThemeColor(): Color {
    val colorsToPopulation = Palette.from(this)
        .maximumColorCount(8)
        .generate()
        .swatches
        .associate { it.rgb to it.population }
    val rankedColors = Score.score(colorsToPopulation)
    return Color(rankedColors.first())
}

fun Bitmap.extractGradientColors(): List<Color> {
    val extractedColors = Palette.from(this)
        .maximumColorCount(64)
        .generate()
        .swatches
        .associate { it.rgb to it.population }

    val orderedColors = Score.score(extractedColors, 2, 0xff4285f4.toInt(), true)
        .sortedByDescending { Color(it).luminance() }

    return if (orderedColors.size >= 2)
        listOf(Color(orderedColors[0]), Color(orderedColors[1]))
    else
        listOf(Color(0xFF595959), Color(0xFF0D0D0D))
}

fun ColorScheme.pureBlack(apply: Boolean) =
    if (apply) copy(
        surface = Color.Black,
        background = Color.Black
    ) else this

val ColorSaver = object : Saver<Color, Int> {
    override fun restore(value: Int): Color = Color(value)
    override fun SaverScope.save(value: Color): Int = value.toArgb()
}
