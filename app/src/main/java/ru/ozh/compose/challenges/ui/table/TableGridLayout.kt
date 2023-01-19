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

    Log.i("TAGGGG", "content $content")

    val itemSizePx = with(LocalDensity.current) { itemSize.roundToPx() }

    Box {


        Layout(
            modifier = modifier.drag(state),
            content = content
        ) { measurables, constraints ->

            measurables.forEach {
                Log.i("TAGGGG", "measurables $it")
            }

            val itemConstraints = Constraints.fixed(
                width = (itemSizePx * state.scale).toInt(),
                height = (itemSizePx * state.scale).toInt()
            )

            val placeables = measurables.map { it.measure(itemConstraints) }
            val cells = elements.map {
                Cell(it.group - 1, it.period - 1)
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
//        Log.i("TAGGGG", "layoutHeightPx ${config.layoutHeightPx}")
//        Log.i("TAGGGG", "layoutWidthPx ${config.layoutWidthPx}")
//        Log.i("TAGGGG", "contentHeight ${config.contentHeight}")
//        Log.i("TAGGGG", "contentWidth ${config.contentWidth}")
//        config.itemSizePx = 500
//        Log.i("TAGGGG", "contentWidth ${config.contentWidth}")
//        Log.i("TAGGGG", "maxOffsetHorizontal ${config.maxOffsetHorizontal}")
//        Log.i("TAGGGG", "maxOffsetVertical ${config.maxOffsetVertical}")
//        Log.i("TAGGGG", "overScrollDragDistanceHorizontal ${config.overScrollDragDistanceHorizontal}")
//        Log.i("TAGGGG", "overScrollDragDistanceVertical ${config.overScrollDragDistanceVertical}")
//        Log.i("TAGGGG", "overScrollDistanceHorizontal ${config.overScrollDistanceHorizontal}")
//        Log.i("TAGGGG", "overScrollDistanceVertical ${config.overScrollDistanceVertical}")
//        Log.i("TAGGGG", "overScrollDragRangeVertical ${config.overScrollDragRangeVertical}")
//        Log.i("TAGGGG", "overScrollDragRangeHorizontal ${config.overScrollDragRangeHorizontal}")
//        Log.i("TAGGGG", "overScrollRangeVertical ${config.overScrollRangeVertical}")
//        Log.i("TAGGGG", "overScrollRangeHorizontal ${config.overScrollRangeHorizontal}")

            layout(constraints.maxWidth, constraints.maxHeight) {
                placeables.forEachIndexed { index, placeable ->
//                placeable.place(
//                    x = cells[index].x * itemSizePx,
//                    y = cells[index].y * itemSizePx
//                )
//                Log.i("TAGG", "layout")
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

        Layout(
            content = {
                (1..10).forEach {
                    Box(modifier = Modifier
                        .size(50.dp, 100.dp)
                    ) {
                        Text(modifier = Modifier.align(alignment = Alignment.Center), text = "$it")
                    }
                }
            },
            modifier = Modifier
                .background(Color.White)
        ) { measurables, constraints ->

            val placeables = measurables.map { it.measure(constraints) }

            val width = placeables.maxOf { it.width }

            layout(width, constraints.maxHeight) {
                var currentheight = state.currentOffset.y.toInt() + state.config.halfItemSizePx
                placeables.forEach {
                    it.place(0, currentheight)
                    currentheight += it.height
                }
            }
        }

        Layout(
            content = {
                (1..18).forEach {
                    Box(
                        modifier = Modifier
                            .size(100.dp, 50.dp)
                    ) {
                        Text(modifier = Modifier.align(alignment = Alignment.Center), text = "$it")
                    }
                }
            },
            modifier = Modifier
                .background(Color.White)
        ) { measurables, constraints ->

            val placeables = measurables.map { it.measure(constraints) }

            val height = placeables.maxOf { it.height }

            layout(constraints.maxWidth, height) {
                var currentWidth = state.currentOffset.x.toInt() + state.config.halfItemSizePx
                placeables.forEach {
                    it.place(currentWidth, 0)
                    currentWidth += it.width
                }
            }
        }
        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(0.dp, 0.dp)
                .background(Color.White)
        )
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