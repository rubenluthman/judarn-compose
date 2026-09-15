package design.judarn.compose

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sin

/**
 * Mathematical conversion from OKLCh coordinates (L, C, H) to Compose sRGB [Color].
 * Lightness: 0.0 to 1.0, Chroma: typically 0.0 to 0.4, Hue: 0.0 to 360.0 degrees.
 */
public fun oklch(l: Float, c: Float, h: Float, alpha: Float = 1.0f): Color {
    val hRad = Math.toRadians(h.toDouble())
    val a = c * cos(hRad)
    val b = c * sin(hRad)

    val lLms = l + 0.3963377774 * a + 0.2158037573 * b
    val mLms = l - 0.1055613458 * a - 0.0638541728 * b
    val sLms = l - 0.0894841775 * a - 1.2914855480 * b

    val lCubed = lLms * lLms * lLms
    val mCubed = mLms * mLms * mLms
    val sCubed = sLms * sLms * sLms

    val rLinear = +4.0767416621 * lCubed - 3.3077115913 * mCubed + 0.2309699292 * sCubed
    val gLinear = -1.2684380046 * lCubed + 2.6097574011 * mCubed - 0.3413193965 * sCubed
    val bLinear = -0.0041960863 * lCubed - 0.7034186147 * mCubed + 1.7076147010 * sCubed

    fun gamma(v: Double): Float {
        val clamped = max(0.0, min(1.0, v))
        val srgb = if (clamped <= 0.0031308) {
            12.92 * clamped
        } else {
            1.055 * clamped.pow(1.0 / 2.4) - 0.055
        }
        return max(0f, min(1f, srgb.toFloat()))
    }

    return Color(
        red = gamma(rLinear),
        green = gamma(gLinear),
        blue = gamma(bLinear),
        alpha = alpha
    )
}

/**
 * 4-tier surface taxonomy and perceptual OKLCh neutrals for the Judarn design system.
 */
@Immutable
public data class JudarnColors(
    val substrate: Color,
    val surfaceElevated: Color,
    val surfaceRecessed: Color,
    val ink100: Color,
    val ink60: Color,
    val ink30: Color,
    val signalError: Color,
    val accent: Color,
    val accent2: Color,
    val pureBlack: Color = Color(0xFF000000),
    val pureWhite: Color = Color(0xFFFFFFFF),
    val isDark: Boolean = false,
    val isHighContrast: Boolean = false
) {
    // Semantic aliases
    val black: Color get() = ink100
    val white: Color get() = surfaceElevated
    val paper: Color get() = substrate
    val line: Color get() = ink100
    val fg1: Color get() = ink100
    val fg2: Color get() = ink60
    val fg3: Color get() = ink30
    val bg1: Color get() = substrate
    val bg2: Color get() = surfaceElevated

    public companion object {
        public val pureBlack: Color = Color(0xFF000000)
        public val pureWhite: Color = Color(0xFFFFFFFF)

        /**
         * Calibrated light theme palette: Warm Modernist Paper (#F5F3EE) base.
         */
        public fun light(
            accent: Color = oklch(0.080f, 0.0f, 0.0f),
            accent2: Color = oklch(0.520f, 0.005f, 92.0f),
            isHighContrast: Boolean = false
        ): JudarnColors {
            val primaryInk = oklch(0.080f, 0.0f, 0.0f) // #0C0C0C (>17:1 AAA)
            return JudarnColors(
                substrate = oklch(0.962f, 0.006f, 92.0f),       // #F5F3EE
                surfaceElevated = pureWhite,                     // Pure White
                surfaceRecessed = oklch(0.935f, 0.008f, 92.0f),  // #EDEAE3 Sunken well
                ink100 = primaryInk,
                ink60 = if (isHighContrast) primaryInk else oklch(0.520f, 0.005f, 92.0f), // #6B6A68 (>5.3:1 AA)
                ink30 = if (isHighContrast) primaryInk else oklch(0.780f, 0.005f, 92.0f), // #C2C1BD Hairline / Disabled
                signalError = if (isHighContrast) oklch(0.415f, 0.240f, 25.0f) else oklch(0.550f, 0.220f, 25.0f),
                accent = accent,
                accent2 = accent2,
                pureBlack = pureBlack,
                pureWhite = pureWhite,
                isDark = false,
                isHighContrast = isHighContrast
            )
        }

        /**
         * Calibrated Dual-Tier Carbon dark theme palette (#171717 base, #222222 elevated).
         */
        public fun dark(
            accent: Color = oklch(0.980f, 0.0f, 0.0f),
            accent2: Color = oklch(0.720f, 0.005f, 92.0f),
            isHighContrast: Boolean = false
        ): JudarnColors {
            val primaryInk = oklch(0.980f, 0.0f, 0.0f) // #FAFAFA (>17:1 AAA)
            return JudarnColors(
                substrate = oklch(0.165f, 0.002f, 92.0f),       // #171717 Deep Carbon
                surfaceElevated = oklch(0.210f, 0.003f, 92.0f),  // #222222 Carbon Surface
                surfaceRecessed = oklch(0.120f, 0.002f, 92.0f),  // #101010 Sunken well
                ink100 = primaryInk,
                ink60 = if (isHighContrast) primaryInk else oklch(0.720f, 0.005f, 92.0f), // #A8A7A5 (>6.4:1 AA)
                ink30 = if (isHighContrast) primaryInk else oklch(0.350f, 0.004f, 92.0f), // #474746 Hairline / Disabled
                signalError = if (isHighContrast) oklch(0.760f, 0.180f, 25.0f) else oklch(0.680f, 0.200f, 25.0f),
                accent = accent,
                accent2 = accent2,
                pureBlack = pureBlack,
                pureWhite = pureWhite,
                isDark = true,
                isHighContrast = isHighContrast
            )
        }

        public fun lightHighContrast(
            accent: Color = oklch(0.080f, 0.0f, 0.0f),
            accent2: Color = oklch(0.080f, 0.0f, 0.0f)
        ): JudarnColors = light(accent = accent, accent2 = accent2, isHighContrast = true)

        public fun darkHighContrast(
            accent: Color = oklch(0.980f, 0.0f, 0.0f),
            accent2: Color = oklch(0.980f, 0.0f, 0.0f)
        ): JudarnColors = dark(accent = accent, accent2 = accent2, isHighContrast = true)
    }
}

/**
 * Resolves the optimal high-contrast foreground ink (pureBlack or pureWhite)
 * based on the background color's perceived relative luminance.
 */
public val Color.contrastingInk: Color
    get() {
        val lum = 0.2126 * red + 0.7152 * green + 0.0722 * blue
        return if (lum > 0.5) JudarnColors.pureBlack else JudarnColors.pureWhite
    }

public val LocalJudarnColors = staticCompositionLocalOf { JudarnColors.light() }
