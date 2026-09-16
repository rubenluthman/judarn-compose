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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

/**
 * Modernist card component with Material 3 corner radii,
 * solid SurfaceElevated fill, and host-symbiotic platform alignment.
 */
@Composable
public fun JudarnCard(
    modifier: Modifier = Modifier,
    eyebrow: String? = null,
    title: String? = null,
    isFlat: Boolean = false,
    useGlassBackground: Boolean = false,
    media: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val colors = JudarnTheme.colors
    val typography = JudarnTheme.typography

    val cardShape = if (isFlat) RoundedCornerShape(0) else RoundedCornerShape(JudarnRadius.card)
    val bgModifier = if (isFlat) Modifier else Modifier.background(colors.surfaceElevated, cardShape).clip(cardShape)
    val padModifier = if (isFlat) Modifier else Modifier.padding(JudarnSpacing.space3)

    Column(
        modifier = modifier
            .then(bgModifier)
            .then(padModifier),
        verticalArrangement = Arrangement.spacedBy(JudarnSpacing.gapNormal)
    ) {
        // Media Frame (3:2 Aspect Ratio)
        if (media != null) {
            val mediaShape = RoundedCornerShape(JudarnRadius.control)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 2f)
                    .background(colors.surfaceElevated, mediaShape)
                    .clip(mediaShape),
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
