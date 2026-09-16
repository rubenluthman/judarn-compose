package design.judarn.compose

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Strict 8dp macro grid and 4dp micro subgrid for the Judarn design system.
 */
public object JudarnSpacing {
    /** 4dp micro spacing / hairline offset / typographic leading sub-step */
    public val spaceHalf: Dp = 4.dp
    /** 8dp tight spacing / gap (macro base unit) */
    public val space1: Dp = 8.dp
    /** 16dp standard padding / gap */
    public val space2: Dp = 16.dp
    /** 24dp medium frame padding */
    public val space3: Dp = 24.dp
    /** 32dp large frame padding / desktop touch target */
    public val space4: Dp = 32.dp
    /** 48dp Material Design 3 touch target floor (12 * 4dp) */
    public val spaceTouch: Dp = 48.dp
    /** 48dp extra large spacing / major control height */
    public val space6: Dp = 48.dp
    /** 64dp layout section spacing */
    public val space8: Dp = 64.dp
    /** 96dp display header spacing */
    public val space12: Dp = 96.dp
    /** 128dp extreme layout spacing */
    public val space16: Dp = 128.dp

    // Semantic aliases
    public val padDefault: Dp = 16.dp
    public val padLarge: Dp = 32.dp
    public val gapTight: Dp = 8.dp
    public val gapNormal: Dp = 16.dp

    // Border and hairline rules
    public val rule: Dp = 1.dp
    public val ruleBold: Dp = 2.dp
    public val ruleHeavy: Dp = 4.dp

    // Typographic reading measures
    /** 640dp maximum column measure for continuous body reading (~65ch) */
    public val measureBody: Dp = 640.dp
    /** 440dp compact column measure for cards and sidebars (~45ch) */
    public val measureCompact: Dp = 440.dp
}

/**
 * Host platform corner radius tokens for Android Compose environments.
 */
public object JudarnRadius {
    /** 6dp subtle rounding for micro elements */
    public val subtle: Dp = 6.dp
    /** 10dp corner radius for functional interactive controls, text fields, and buttons */
    public val control: Dp = 10.dp
    /** 16dp corner radius for content cards, grouping tiles, and sheets */
    public val card: Dp = 16.dp
    /** 9999dp pill/capsule shape for badges, tags, and status chips */
    public val badge: Dp = 9999.dp
}

