package design.judarn.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor

/**
 * Modernist input field with 1dp hairline resting border expanding to 2dp
 * on active focus or validation error, zero corner radius, and SignalError color mapping.
 */
@Composable
public fun JudarnTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "",
    hint: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val colors = JudarnTheme.colors
    val typography = JudarnTheme.typography

    val borderColor = when {
        isError -> colors.signalError
        isFocused -> colors.accent
        else -> colors.ink100
    }

    val borderWidth = if (colors.isHighContrast || isFocused || isError) JudarnSpacing.ruleBold else JudarnSpacing.rule

    Column(modifier = modifier, verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(JudarnSpacing.gapTight)) {
        if (label != null) {
            Text(
                text = label.uppercase(),
                style = typography.label,
                color = colors.ink100
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .border(borderWidth, borderColor, RectangleShape)
                .background(colors.surfaceElevated, RectangleShape)
                .padding(horizontal = JudarnSpacing.space2, vertical = JudarnSpacing.space2),
            enabled = enabled,
            singleLine = singleLine,
            textStyle = typography.body.copy(color = colors.ink100),
            cursorBrush = SolidColor(colors.ink100),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                if (value.isEmpty() && placeholder.isNotEmpty()) {
                    Text(
                        text = placeholder,
                        style = typography.body,
                        color = colors.ink30
                    )
                }
                innerTextField()
            }
        )

        if (hint != null) {
            Text(
                text = hint,
                style = typography.small,
                color = if (isError) colors.signalError else colors.ink60
            )
        }
    }
}
