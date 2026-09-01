package design.judarn.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape

/**
 * Modernist card component with 1dp hairline border, 0-radius corners,
 * and solid SurfaceElevated fill.
 */
@Composable
public fun JudarnCard(
    modifier: Modifier = Modifier,
    eyebrow: String? = null,
    title: String? = null,
    isFlat: Boolean = false,
    actions: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val colors = JudarnTheme.colors
    val typography = JudarnTheme.typography

    val borderModifier = if (isFlat) Modifier else Modifier.border(JudarnSpacing.rule, colors.ink100, RectangleShape)
    val bgModifier = if (isFlat) Modifier else Modifier.background(colors.surfaceElevated, RectangleShape)
    val padModifier = if (isFlat) Modifier else Modifier.padding(JudarnSpacing.space3)

    Column(
        modifier = modifier
            .then(borderModifier)
            .then(bgModifier)
            .then(padModifier),
        verticalArrangement = Arrangement.spacedBy(JudarnSpacing.gapNormal)
    ) {
        if (eyebrow != null) {
            Text(
                text = eyebrow.uppercase(),
                style = typography.label,
                color = colors.accent
            )
        }

        if (title != null) {
            Text(
                text = title,
                style = typography.h4,
                color = colors.ink100
            )
        }

        content()

        if (actions != null) {
            Row(horizontalArrangement = Arrangement.spacedBy(JudarnSpacing.gapTight)) {
                actions()
            }
        }
    }
}
