package design.judarn.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

public enum class JudarnLogoLockupVariant {
    Horizontal,
    Vertical,
    Icon,
    Reversed
}

/**
 * Placeholder geometric logo mark, constructed from primitives on the 8dp grid.
 */
@Composable
public fun JudarnPlaceholderMark(
    size: Dp = 48.dp,
    modifier: Modifier = Modifier
) {
    val colors = JudarnTheme.colors

    Box(
        modifier = modifier
            .size(size)
    ) {
        Column(modifier = Modifier.size(size)) {
            // Top Half
            Box(
                modifier = Modifier
                    .size(width = size, height = size / 2)
                    .background(colors.ink100, RectangleShape)
            )

            // Bottom Half
            Row(modifier = Modifier.size(width = size, height = size / 2)) {
                Box(
                    modifier = Modifier
                        .size(size / 2)
                        .background(colors.ink60, RectangleShape)
                )

                Canvas(modifier = Modifier.size(size / 2)) {
                    val path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(this@Canvas.size.width, 0f)
                        lineTo(this@Canvas.size.width, this@Canvas.size.height)
                        close()
                    }
                    drawPath(path, color = colors.accent)
                }
            }
        }
    }
}

/**
 * Modernist LogoLockup composable. Combines a geometric mark with a bold wordmark.
 */
@Composable
public fun JudarnLogoLockup(
    modifier: Modifier = Modifier,
    name: String = "JUDARN",
    tagline: String? = null,
    variant: JudarnLogoLockupVariant = JudarnLogoLockupVariant.Horizontal,
    markSize: Dp? = null,
    mark: (@Composable () -> Unit)? = null
) {
    val colors = JudarnTheme.colors
    val typography = JudarnTheme.typography

    val defaultMarkSize = when (variant) {
        JudarnLogoLockupVariant.Vertical -> 64.dp
        JudarnLogoLockupVariant.Icon -> 64.dp
        else -> 48.dp
    }
    val resolvedSize = markSize ?: defaultMarkSize

    val markContent: @Composable () -> Unit = {
        if (mark != null) {
            mark()
        } else {
            JudarnPlaceholderMark(size = resolvedSize)
        }
    }

    if (variant == JudarnLogoLockupVariant.Icon) {
        Box(modifier = modifier) {
            markContent()
        }
        return
    }

    val isReversed = variant == JudarnLogoLockupVariant.Reversed
    val backgroundModifier = if (isReversed) {
        Modifier
            .background(colors.ink100, RectangleShape)
            .padding(JudarnSpacing.space3)
    } else {
        Modifier
    }

    val wordsContent: @Composable () -> Unit = {
        Column(
            verticalArrangement = Arrangement.spacedBy(JudarnSpacing.spaceHalf)
        ) {
            Text(
                text = name,
                style = typography.h4,
                color = if (isReversed) colors.surfaceElevated else colors.ink100
            )

            if (tagline != null) {
                Text(
                    text = tagline.uppercase(),
                    style = typography.label,
                    color = if (isReversed) colors.ink30 else colors.fg2
                )
            }
        }
    }

    when (variant) {
        JudarnLogoLockupVariant.Vertical -> {
            Column(
                modifier = modifier.then(backgroundModifier),
                verticalArrangement = Arrangement.spacedBy(JudarnSpacing.space2),
                horizontalAlignment = Alignment.Start
            ) {
                markContent()
                wordsContent()
            }
        }
        else -> {
            Row(
                modifier = modifier.then(backgroundModifier),
                horizontalArrangement = Arrangement.spacedBy(JudarnSpacing.space2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                markContent()
                wordsContent()
            }
        }
    }
}
