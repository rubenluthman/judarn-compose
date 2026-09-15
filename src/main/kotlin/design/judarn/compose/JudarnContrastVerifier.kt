package design.judarn.compose

import androidx.compose.ui.graphics.Color
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

/**
 * WCAG 2.1 / 2.2 contrast compliance tier.
 */
public enum class JudarnWCAGLevel {
    AA,
    AAA
}

/**
 * Typographic and UI element context for WCAG contrast evaluation.
 */
public enum class JudarnContrastContext {
    /** Standard body and data text (<18pt regular, <14pt bold). AA requires 4.5:1, AAA requires 7.0:1. */
    NormalText,
    /** Large titles and headings (>=18pt regular, >=14pt bold). AA requires 3.0:1, AAA requires 4.5:1. */
    LargeText,
    /** Graphical elements, interactive boundaries, and hairline controls. AA requires 3.0:1. */
    GraphicalComponent;

    public fun minimumRatio(level: JudarnWCAGLevel): Double {
        return when (this) {
            NormalText -> if (level == JudarnWCAGLevel.AAA) 7.0 else 4.5
            LargeText -> if (level == JudarnWCAGLevel.AAA) 4.5 else 3.0
            GraphicalComponent -> 3.0
        }
    }
}

/**
 * Appearance mode context for resolving dynamic color palettes.
 */
public enum class JudarnAppearanceMode {
    Light,
    Dark,
    LightHighContrast,
    DarkHighContrast
}

/**
 * Verification report for a foreground and background color pairing.
 */
public data class JudarnContrastAuditResult(
    val foregroundName: String,
    val backgroundName: String,
    val appearance: JudarnAppearanceMode,
    val contrastRatio: Double,
    val normalTextAAA: Boolean = contrastRatio >= JudarnContrastContext.NormalText.minimumRatio(JudarnWCAGLevel.AAA),
    val normalTextAA: Boolean = contrastRatio >= JudarnContrastContext.NormalText.minimumRatio(JudarnWCAGLevel.AA),
    val largeTextAAA: Boolean = contrastRatio >= JudarnContrastContext.LargeText.minimumRatio(JudarnWCAGLevel.AAA),
    val largeTextAA: Boolean = contrastRatio >= JudarnContrastContext.LargeText.minimumRatio(JudarnWCAGLevel.AA),
    val graphicalAA: Boolean = contrastRatio >= JudarnContrastContext.GraphicalComponent.minimumRatio(JudarnWCAGLevel.AA)
)

/**
 * Mathematical and appearance-aware WCAG AAA/AA contrast verifier for Judarn color tokens and surface pairings.
 */
public object JudarnContrastVerifier {
    /**
     * Linearizes an sRGB component in the range [0.0, 1.0] according to W3C WCAG 2.1 specs.
     */
    public fun linearize(sRGBComponent: Double): Double {
        val clamped = max(0.0, min(1.0, sRGBComponent))
        return if (clamped <= 0.04045) {
            clamped / 12.92
        } else {
            ((clamped + 0.055) / 1.055).pow(2.4)
        }
    }

    /**
     * Computes relative luminance (Y) from sRGB red, green, and blue components in [0.0, 1.0].
     */
    public fun relativeLuminance(red: Double, green: Double, blue: Double): Double {
        val rLin = linearize(red)
        val gLin = linearize(green)
        val bLin = linearize(blue)
        return 0.2126 * rLin + 0.7152 * gLin + 0.0722 * bLin
    }

    /**
     * Calculates relative luminance for a Compose [Color].
     */
    public fun relativeLuminance(color: Color): Double {
        return relativeLuminance(color.red.toDouble(), color.green.toDouble(), color.blue.toDouble())
    }

    /**
     * Calculates the WCAG contrast ratio between two relative luminance values.
     * Returns a value between 1.0 (no contrast) and 21.0 (maximum contrast).
     */
    public fun contrastRatio(luminance1: Double, luminance2: Double): Double {
        val lighter = max(luminance1, luminance2)
        val darker = min(luminance1, luminance2)
        return (lighter + 0.05) / (darker + 0.05)
    }

    /**
     * Calculates the WCAG contrast ratio between two Compose [Color] values.
     */
    public fun contrastRatio(foreground: Color, background: Color): Double {
        val l1 = relativeLuminance(foreground)
        val l2 = relativeLuminance(background)
        return contrastRatio(l1, l2)
    }

    /**
     * Audits a specific foreground/background pair in a given appearance mode.
     */
    public fun auditPair(
        foreground: Color,
        foregroundName: String,
        background: Color,
        backgroundName: String,
        appearance: JudarnAppearanceMode
    ): JudarnContrastAuditResult {
        val ratio = contrastRatio(foreground, background)
        return JudarnContrastAuditResult(
            foregroundName = foregroundName,
            backgroundName = backgroundName,
            appearance = appearance,
            contrastRatio = ratio
        )
    }

    /**
     * Audits the entire core token matrix across all surfaces and appearances (48 total evaluations).
     */
    public fun auditCoreMatrix(): List<JudarnContrastAuditResult> {
        val results = mutableListOf<JudarnContrastAuditResult>()

        for (mode in JudarnAppearanceMode.values()) {
            val palette = when (mode) {
                JudarnAppearanceMode.Light -> JudarnColors.light()
                JudarnAppearanceMode.Dark -> JudarnColors.dark()
                JudarnAppearanceMode.LightHighContrast -> JudarnColors.lightHighContrast()
                JudarnAppearanceMode.DarkHighContrast -> JudarnColors.darkHighContrast()
            }

            val foregrounds = listOf(
                "ink100" to palette.ink100,
                "ink60" to palette.ink60,
                "ink30" to palette.ink30,
                "signalError" to palette.signalError
            )

            val surfaces = listOf(
                "substrate" to palette.substrate,
                "surfaceElevated" to palette.surfaceElevated,
                "surfaceRecessed" to palette.surfaceRecessed
            )

            for ((bgName, bg) in surfaces) {
                for ((fgName, fg) in foregrounds) {
                    results.add(
                        auditPair(
                            foreground = fg,
                            foregroundName = fgName,
                            background = bg,
                            backgroundName = bgName,
                            appearance = mode
                        )
                    )
                }
            }
        }

        return results
    }
}

/**
 * Computes the WCAG contrast ratio between this color and another background color.
 */
public fun Color.contrastRatio(background: Color): Double {
    return JudarnContrastVerifier.contrastRatio(this, background)
}

/**
 * Computes the relative luminance of this color.
 */
public fun Color.relativeLuminance(): Double {
    return JudarnContrastVerifier.relativeLuminance(this)
}

/**
 * Asserts whether this color complies with WCAG standards against a given background.
 */
public fun Color.isWCAGCompliant(
    against: Color,
    context: JudarnContrastContext = JudarnContrastContext.NormalText,
    level: JudarnWCAGLevel = JudarnWCAGLevel.AAA
): Boolean {
    val ratio = contrastRatio(against)
    return ratio >= context.minimumRatio(level)
}
