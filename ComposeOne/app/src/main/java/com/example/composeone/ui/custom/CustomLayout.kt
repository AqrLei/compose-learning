package com.example.composeone.ui.custom

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = Modifier.layout{ measurable, constraints ->
    val placeable = measurable.measure(constraints)
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified )
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
    content: @Composable () ->Unit
){
    Layout(modifier = modifier, content = content) { measurables , constraints ->
        val placeables =measurables.map { measurable ->
            // measure each child
            measurable.measure(constraints)
        }
        var yPosition = 0

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(x= 0 , y= yPosition)
                yPosition += placeable.height
            }
        }
    }

}