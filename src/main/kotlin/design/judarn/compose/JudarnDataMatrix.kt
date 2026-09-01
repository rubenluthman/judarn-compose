package design.judarn.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp

public data class JudarnMatrixColumn(
    val id: String,
    val title: String,
    val isNumeric: Boolean = false,
    val width: Dp? = null
)

public data class JudarnMatrixRow(
    val id: String,
    val cells: Map<String, String>
)

/**
 * Modernist continuous 1dp hairline data matrix with tabular numeral formatting.
 */
@Composable
public fun JudarnDataMatrix(
    columns: List<JudarnMatrixColumn>,
    rows: List<JudarnMatrixRow>,
    modifier: Modifier = Modifier
) {
    val colors = JudarnTheme.colors
    val typography = JudarnTheme.typography

    Column(
        modifier = modifier
            .border(JudarnSpacing.rule, colors.ink100, RectangleShape)
            .background(colors.surfaceElevated, RectangleShape)
    ) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.surfaceElevated),
            verticalAlignment = Alignment.CenterVertically
        ) {
            columns.forEachIndexed { index, col ->
                val colMod = if (col.width != null) Modifier.width(col.width) else Modifier.weight(1f)
                Box(
                    modifier = colMod.padding(horizontal = JudarnSpacing.space2, vertical = JudarnSpacing.space1),
                    contentAlignment = if (col.isNumeric) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Text(
                        text = col.title.uppercase(),
                        style = typography.label,
                        color = colors.ink100,
                        textAlign = if (col.isNumeric) TextAlign.End else TextAlign.Start
                    )
                }
                if (index < columns.size - 1) {
                    VerticalDivider(thickness = JudarnSpacing.rule, color = colors.ink100)
                }
            }
        }

        // Header bottom border (2dp bold rule)
        HorizontalDivider(thickness = JudarnSpacing.ruleBold, color = colors.ink100)

        // Data Rows
        rows.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.surfaceElevated),
                verticalAlignment = Alignment.CenterVertically
            ) {
                columns.forEachIndexed { colIndex, col ->
                    val colMod = if (col.width != null) Modifier.width(col.width) else Modifier.weight(1f)
                    Box(
                        modifier = colMod.padding(horizontal = JudarnSpacing.space2, vertical = JudarnSpacing.space1),
                        contentAlignment = if (col.isNumeric) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Text(
                            text = row.cells[col.id].orEmpty(),
                            style = typography.small.copy(
                                fontFamily = if (col.isNumeric) FontFamily.Monospace else FontFamily.SansSerif
                            ),
                            color = colors.ink100,
                            textAlign = if (col.isNumeric) TextAlign.End else TextAlign.Start
                        )
                    }
                    if (colIndex < columns.size - 1) {
                        VerticalDivider(thickness = JudarnSpacing.rule, color = colors.ink30)
                    }
                }
            }
            if (rowIndex < rows.size - 1) {
                HorizontalDivider(thickness = JudarnSpacing.rule, color = colors.ink30)
            }
        }
    }
}
