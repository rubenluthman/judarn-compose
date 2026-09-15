package design.judarn.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.CollectionItemInfo
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.collectionItemInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
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
 * Preserves font baseline and line height for empty cells using a zero-width space glyph.
 */
private fun resolvedText(value: String?): String {
    return if (value.isNullOrEmpty()) "\u200B" else value
}

/**
 * Modernist continuous hairline data matrix with tabular numeral formatting,
 * accessibility landmark semantics, and high-contrast rule scaling.
 */
@Composable
public fun JudarnDataMatrix(
    columns: List<JudarnMatrixColumn>,
    rows: List<JudarnMatrixRow>,
    modifier: Modifier = Modifier
) {
    val colors = JudarnTheme.colors
    val typography = JudarnTheme.typography
    val dividerRule = if (colors.isHighContrast) JudarnSpacing.ruleBold else JudarnSpacing.rule

    Column(
        modifier = modifier
            .border(dividerRule, colors.ink100, RectangleShape)
            .background(colors.surfaceElevated, RectangleShape)
            .semantics {
                contentDescription = "Data Matrix, ${columns.size} columns, ${rows.size} rows"
                collectionInfo = CollectionInfo(rowCount = rows.size + 1, columnCount = columns.size)
            }
    ) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.surfaceElevated)
                .semantics {
                    contentDescription = "Header Row, ${columns.size} columns"
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            columns.forEachIndexed { index, col ->
                val colMod = if (col.width != null) {
                    Modifier.defaultMinSize(minWidth = col.width).width(col.width)
                } else {
                    Modifier.weight(1f)
                }
                Box(
                    modifier = colMod
                        .padding(horizontal = JudarnSpacing.space2, vertical = JudarnSpacing.space1)
                        .semantics {
                            heading()
                            contentDescription = "${col.title} (Column ${index + 1} of ${columns.size})"
                            collectionItemInfo = CollectionItemInfo(
                                rowIndex = 0,
                                rowSpan = 1,
                                columnIndex = index,
                                columnSpan = 1
                            )
                        },
                    contentAlignment = if (col.isNumeric) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Text(
                        text = resolvedText(col.title).uppercase(),
                        style = typography.label,
                        color = colors.ink100,
                        textAlign = if (col.isNumeric) TextAlign.End else TextAlign.Start
                    )
                }
                if (index < columns.size - 1) {
                    VerticalDivider(
                        modifier = Modifier.clearAndSetSemantics { },
                        thickness = JudarnSpacing.rule,
                        color = colors.ink100
                    )
                }
            }
        }

        // Header bottom border (2dp bold rule)
        HorizontalDivider(
            modifier = Modifier.clearAndSetSemantics { },
            thickness = JudarnSpacing.ruleBold,
            color = colors.ink100
        )

        // Data Rows
        rows.forEachIndexed { rowIndex, row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.surfaceElevated)
                    .semantics {
                        contentDescription = "Row ${rowIndex + 1} of ${rows.size}"
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                columns.forEachIndexed { colIndex, col ->
                    val colMod = if (col.width != null) {
                        Modifier.defaultMinSize(minWidth = col.width).width(col.width)
                    } else {
                        Modifier.weight(1f)
                    }
                    val rawValue = row.cells[col.id]
                    val cellSpoken = if (rawValue.isNullOrEmpty()) "Empty" else rawValue
                    val coordinateHint = "Row ${rowIndex + 1} of ${rows.size}, Column ${colIndex + 1} of ${columns.size}"

                    Box(
                        modifier = colMod
                            .padding(horizontal = JudarnSpacing.space2, vertical = JudarnSpacing.space1)
                            .semantics {
                                contentDescription = "${col.title}: $cellSpoken, $coordinateHint"
                                collectionItemInfo = CollectionItemInfo(
                                    rowIndex = rowIndex + 1,
                                    rowSpan = 1,
                                    columnIndex = colIndex,
                                    columnSpan = 1
                                )
                            },
                        contentAlignment = if (col.isNumeric) Alignment.CenterEnd else Alignment.CenterStart
                    ) {
                        Text(
                            text = resolvedText(rawValue),
                            style = typography.small.copy(
                                fontFamily = if (col.isNumeric) FontFamily.Monospace else FontFamily.SansSerif
                            ),
                            color = colors.ink100,
                            textAlign = if (col.isNumeric) TextAlign.End else TextAlign.Start
                        )
                    }
                    if (colIndex < columns.size - 1) {
                        VerticalDivider(
                            modifier = Modifier.clearAndSetSemantics { },
                            thickness = dividerRule,
                            color = colors.ink30
                        )
                    }
                }
            }
            if (rowIndex < rows.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.clearAndSetSemantics { },
                    thickness = dividerRule,
                    color = colors.ink30
                )
            }
        }
    }
}

