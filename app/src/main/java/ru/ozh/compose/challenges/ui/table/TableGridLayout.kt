package ru.ozh.compose.challenges.ui.table

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import ru.ozh.compose.challenges.ui.grid.*
import ru.ozh.compose.challenges.ui.table.data.Elements.elements

@Composable
fun TableGridLayout(
    modifier: Modifier,
    itemSize: Dp,
    state: WatchGridState = rememberWatchGridState(),
    content: @Composable () -> Unit
    ){

    val itemSizePx = with(LocalDensity.current) { itemSize.roundToPx() }

    Layout(
        modifier = modifier
            .drag(state),
        content = content
    ) { measurables, constraints ->


        val itemConstraints = Constraints.fixed(width = (itemSizePx * state.scale).toInt(), height = (itemSizePx * state.scale).toInt())

        val placeables = measurables.map { it.measure(itemConstraints) }
        val cells = elements.map {
            Cell(it.group - 1, it.period - 1)
        }.toList()


        state.setup(
            WatchGridConfig(
                itemSizePx = itemSizePx,
                layoutHeightPx = constraints.maxHeight,
                layoutWidthPx = constraints.maxWidth,
                cells = cells
            )
        )

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEachIndexed { index, placeable ->
//                placeable.place(
//                    x = cells[index].x * itemSizePx,
//                    y = cells[index].y * itemSizePx
//                )
                Log.i("TAGG", "layout")
                val position = state.getPositionFor(index)
                val scale = state.getScaleFor(position)
                placeable.placeWithLayer(
                    position = position,
//                    layerBlock = {
//                        this.scaleX = scale
//                        this.scaleY = scale
//                    }
                )
            }
        }
    }

}