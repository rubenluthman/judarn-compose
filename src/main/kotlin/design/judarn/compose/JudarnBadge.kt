package design.judarn.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

public enum class JudarnBadgeVariant {
    Outline,
    Solid,
    Accent,
    AccentOutline
}

/**
 * Status badge with uppercase tracked label and 1dp hairline border.
 */
@Composable
public fun JudarnBadge(
    text: String,
    modifier: Modifier = Modifier,
    variant: JudarnBadgeVariant = JudarnBadgeVariant.Outline
) {
    val colors = JudarnTheme.colors
    val typography = JudarnTheme.typography

    val (bgColor, fgColor, borderColor) = when (variant) {
        JudarnBadgeVariant.Outline -> Triple(colors.surfaceElevated, colors.ink100, colors.ink100)
        JudarnBadgeVariant.Solid -> Triple(colors.ink100, colors.surfaceElevated, colors.ink100)
        JudarnBadgeVariant.Accent -> Triple(colors.accent, colors.accent.contrastingInk, colors.accent)
        JudarnBadgeVariant.AccentOutline -> Triple(colors.surfaceElevated, colors.accent, colors.accent)
    }

    Box(
        modifier = modifier
            .border(JudarnSpacing.rule, borderColor, RectangleShape)
            .background(bgColor, RectangleShape)
            .padding(horizontal = JudarnSpacing.space1, vertical = JudarnSpacing.spaceHalf),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text.uppercase(),
            style = typography.label,
            color = fgColor
        )
    }
}

/**
 * Keyword tag with sentence case and 1dp hairline border.
 */
@Composable
public fun JudarnTag(
    text: String,
    modifier: Modifier = Modifier
) {
    val colors = JudarnTheme.colors
    val typography = JudarnTheme.typography

    Box(
        modifier = modifier
            .border(JudarnSpacing.rule, colors.ink100, RectangleShape)
            .background(colors.surfaceElevated, RectangleShape)
            .padding(horizontal = JudarnSpacing.space1, vertical = JudarnSpacing.spaceHalf),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = typography.small,
            color = colors.ink100
        )
    }
}
