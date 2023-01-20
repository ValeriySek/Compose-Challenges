package ru.ozh.compose.challenges.ui.table

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.ozh.compose.challenges.ui.grid.*
import ru.ozh.compose.challenges.ui.table.data.Elements.elements

@Composable
fun TableGridLayout(
    modifier: Modifier,
    itemSize: Dp,
    state: WatchGridState = rememberWatchGridState(),
    content: @Composable () -> Unit
) {

    val itemSizePx = with(LocalDensity.current) { itemSize.roundToPx() }

        Layout(
            modifier = modifier.drag(state),
            content = content
        ) { measurables, constraints ->

            val itemConstraints = Constraints.fixed(
                width = (itemSizePx * state.scale).toInt(),
                height = (itemSizePx * state.scale).toInt()
            )

            val itemPlaceables = measurables.filter { it.layoutId == "Item" }.map { it.measure(itemConstraints) }
            val verticalAxisPlaceables = measurables.filter { it.layoutId == "VerticalAxis" }.map { it.measure(constraints) }
            val verticalAxisWidth = verticalAxisPlaceables.maxOf { it.width }
            val horizontalAxisPlaceables = measurables.filter { it.layoutId == "HorizontalAxis" }.map { it.measure(constraints) }
            val horizontalAxisWidth = horizontalAxisPlaceables.maxOf { it.height }
            val boxPlaceable = measurables.firstOrNull { it.layoutId == "Box" }?.measure(constraints)


            val cells = elements.map {
                when (it.elementCategory) {
                    "lanthanide" -> Cell(it.atomicNumber - 55, it.period + 1)
                    "actinide" -> Cell(it.atomicNumber - 87, it.period + 1)
                    else -> Cell(it.group - 1, it.period - 1)
                }
            }.toList()

            val config = getConfig(
                itemSizePx = (itemSizePx * state.scale).toInt(),
                layoutHeightPx = constraints.maxHeight,
                layoutWidthPx = constraints.maxWidth,
                cells = cells
            )
            state.setup(
                config
            )

            layout(constraints.maxWidth, constraints.maxHeight) {
                itemPlaceables.forEachIndexed { index, placeable ->
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
                var currentheight = state.currentOffset.y.toInt() + state.config.halfItemSizePx
                verticalAxisPlaceables.forEach {
                    it.place(0, currentheight)
                    currentheight += it.height
                }
                var currentWidth = state.currentOffset.x.toInt() + state.config.halfItemSizePx
                horizontalAxisPlaceables.forEach {
                    it.place(currentWidth, 0)
                    currentWidth += it.width
                }
                boxPlaceable?.place(0,0)
            }
        }
}

fun getConfig(
    itemSizePx: Int = 0,
    layoutHeightPx: Int = 0,
    layoutWidthPx: Int = 0,
    cells: List<Cell> = emptyList()
): WatchGridConfig {
    Log.i("TAGGGG", "getConfig")
    return WatchGridConfig(
        itemSizePx = itemSizePx,
        layoutHeightPx = layoutHeightPx,
        layoutWidthPx = layoutWidthPx,
        cells = cells
    )
}