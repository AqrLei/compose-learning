package com.example.composeone.ui.custom

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import kotlin.math.absoluteValue

/**
 *
 * When using the layout modifier, you get two lambda parameters:
 * measurable: child to be measured and placed
 * constraints: minimum and maximum for the width and height of the child
 */

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {

        //Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}

@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            // measure each child
            measurable.measure(constraints)
        }
        var yPosition = 0

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }

}

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable() () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->

        val rowWidths = IntArray(rows) { 0 }
        val rowMaxHeights = IntArray(rows) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)
            val row = index % rows
            rowWidths[row] = rowWidths[row] + placeable.width.absoluteValue
            rowMaxHeights[row] = kotlin.math.max(rowMaxHeights[row], placeable.height.absoluteValue)
            placeable
        }
        val width =
            rowWidths.maxOrNull()?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
                ?: constraints.minWidth
        val height = rowMaxHeights.sumBy { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowMaxHeights[i - 1]
        }

        layout(width, height) {
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(x = rowX[row], y = rowY[row])

                rowX[row] += placeable.width
            }
        }
    }
}