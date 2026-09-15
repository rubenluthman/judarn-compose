package design.judarn.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role

public enum class JudarnButtonVariant {
    Primary,
    Outline,
    Secondary,
    Ghost,
    Accent
}

public enum class JudarnButtonSize {
    Sm,
    Md,
    Lg
}

/**
 * Modernist Button composable conforming to the Judarn interaction model.
 * Enforces a decoupled 48dp touch envelope for Material Design 3 ergonomics
 * while maintaining a compact visual box, 1dp hairline border, 0-radius corners,
 * and instantaneous color inversion without ripples.
 */
@Composable
public fun JudarnButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: JudarnButtonVariant = JudarnButtonVariant.Primary,
    size: JudarnButtonSize = JudarnButtonSize.Md,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val colors = JudarnTheme.colors

    val (bgColor, fgColor, borderColor) = resolveColors(
        variant = variant,
        isHovered = isHovered,
        isPressed = isPressed,
        enabled = enabled,
        colors = colors
    )

    val (hPad, vPad) = when (size) {
        JudarnButtonSize.Sm -> JudarnSpacing.space2 to JudarnSpacing.space1
        JudarnButtonSize.Md -> JudarnSpacing.space3 to JudarnSpacing.space2
        JudarnButtonSize.Lg -> JudarnSpacing.space4 to JudarnSpacing.space3
    }

    // Outer container provides touch envelope (48dp minimum)
    Box(
        modifier = modifier
            .sizeIn(minWidth = JudarnSpacing.spaceTouch, minHeight = JudarnSpacing.spaceTouch)
            .clickable(
                interactionSource = interactionSource,
                indication = null, // Suppress M3 circular ripple
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        // Inner visual box: strict 0-radius, 1dp hairline border
        Box(
            modifier = Modifier
                .border(
                    width = if (variant == JudarnButtonVariant.Ghost) 0.dp else JudarnSpacing.rule,
                    color = borderColor,
                    shape = RectangleShape
                )
                .background(bgColor, shape = RectangleShape)
                .padding(horizontal = hPad, vertical = vPad),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

/**
 * Text overload for JudarnButton.
 */
@Composable
public fun JudarnButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: JudarnButtonVariant = JudarnButtonVariant.Primary,
    size: JudarnButtonSize = JudarnButtonSize.Md,
    enabled: Boolean = true
) {
    val textStyle = when (size) {
        JudarnButtonSize.Sm -> JudarnTheme.typography.small
        JudarnButtonSize.Md -> JudarnTheme.typography.body
        JudarnButtonSize.Lg -> JudarnTheme.typography.bodyLg
    }

    JudarnButton(
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        size = size,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}

private fun resolveColors(
    variant: JudarnButtonVariant,
    isHovered: Boolean,
    isPressed: Boolean,
    enabled: Boolean,
    colors: JudarnColors
): Triple<Color, Color, Color> {
    if (!enabled) {
        return Triple(colors.surfaceRecessed, colors.ink30, colors.ink30)
    }
    if (isPressed) {
        return Triple(colors.ink60, colors.substrate, colors.ink60)
    }

    return when (variant) {
        JudarnButtonVariant.Primary -> {
            if (isHovered) {
                Triple(colors.surfaceElevated, colors.ink100, colors.ink100)
            } else {
                Triple(colors.ink100, colors.substrate, colors.ink100)
            }
        }
        JudarnButtonVariant.Outline -> {
            if (isHovered) {
                Triple(colors.ink100, colors.substrate, colors.ink100)
            } else {
                Triple(Color.Transparent, colors.ink100, colors.ink100)
            }
        }
        JudarnButtonVariant.Secondary -> {
            if (isHovered) {
                Triple(colors.surfaceElevated, colors.ink100, colors.ink60)
            } else {
                Triple(colors.surfaceRecessed, colors.ink100, colors.ink30)
            }
        }
        JudarnButtonVariant.Ghost -> {
            Triple(Color.Transparent, colors.ink100, Color.Transparent)
        }
        JudarnButtonVariant.Accent -> {
            if (isHovered) {
                Triple(colors.surfaceElevated, colors.accent, colors.accent)
            } else {
                Triple(colors.accent, colors.accent.contrastingInk, colors.accent)
            }
        }
    }
}
