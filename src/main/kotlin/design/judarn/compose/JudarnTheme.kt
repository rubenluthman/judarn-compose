package design.judarn.compose

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.DelegatableNode

/**
 * Modernist indication node that suppresses Google Material 3 circular ripple effects,
 * preserving clean, instantaneous modernist state transitions.
 */
private object NoIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return object : Modifier.Node(), DelegatableNode {}
    }
    override fun hashCode(): Int = -1
    override fun equals(other: Any?): Boolean = other === this
}

/**
 * Root theme composable for the Judarn design system in Jetpack Compose.
 */
@Composable
public fun JudarnTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    highContrast: Boolean = false,
    colors: JudarnColors = when {
        darkTheme && highContrast -> JudarnColors.darkHighContrast()
        darkTheme -> JudarnColors.dark()
        highContrast -> JudarnColors.lightHighContrast()
        else -> JudarnColors.light()
    },
    typography: JudarnTypography = JudarnTypography(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalJudarnColors provides colors,
        LocalJudarnTypography provides typography,
        LocalIndication provides NoIndication,
        content = content
    )
}

public object JudarnTheme {
    public val colors: JudarnColors
        @Composable
        @ReadOnlyComposable
        get() = LocalJudarnColors.current

    public val typography: JudarnTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalJudarnTypography.current

    public val spacing: JudarnSpacing
        get() = JudarnSpacing
}
