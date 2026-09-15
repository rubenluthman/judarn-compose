package design.judarn.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    media: @Composable (() -> Unit)? = null,
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
        // Media Frame (3:2 Aspect Ratio, 2dp border)
        if (media != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 2f)
                    .background(colors.surfaceElevated, RectangleShape)
                    .border(JudarnSpacing.ruleBold, colors.ink100, RectangleShape),
                contentAlignment = Alignment.Center
            ) {
                media()
            }
        }

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

        // Unbound content block allows children to retain their own contrast hierarchies
        content()

        if (actions != null) {
            Row(horizontalArrangement = Arrangement.spacedBy(JudarnSpacing.gapTight)) {
                actions()
            }
        }
    }
}

@Deprecated(
    message = "Use solid surfaceElevated substrates. Translucency is strictly reserved for Tier 2 chrome.",
    replaceWith = ReplaceWith("JudarnCard(modifier, eyebrow, title, isFlat, media, actions, content)")
)
@Composable
public fun JudarnCard(
    useGlassBackground: Boolean,
    modifier: Modifier = Modifier,
    eyebrow: String? = null,
    title: String? = null,
    isFlat: Boolean = false,
    media: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    JudarnCard(
        modifier = modifier,
        eyebrow = eyebrow,
        title = title,
        isFlat = isFlat,
        media = media,
        actions = actions,
        content = content
    )
}
