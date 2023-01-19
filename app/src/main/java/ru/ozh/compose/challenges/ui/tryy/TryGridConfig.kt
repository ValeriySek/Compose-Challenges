package ru.ozh.compose.challenges.ui.tryy

import androidx.compose.ui.unit.IntOffset
import ru.ozh.compose.challenges.ui.grid.Cell

class TryGridConfig(
    val itemSizePx: Int = 0,
    val layoutHeightPx: Int = 0,
    val layoutWidthPx: Int = 0,
    val cells: List<Cell> = emptyList()
) {

    val halfItemSizePx = itemSizePx / 2

    val contentHeight = ((cells.maxByOrNull { it.y }?.y?.let { y -> y + 1 }) ?: 0).times(itemSizePx)
    val contentWidth = ((cells.maxByOrNull { it.x }?.x?.let { x -> x + 1 }) ?: 0).times(itemSizePx)

    val maxOffsetHorizontal = contentWidth - layoutWidthPx
    val maxOffsetVertical = contentHeight - layoutHeightPx

    val overScrollDragDistanceHorizontal = layoutWidthPx - itemSizePx
    val overScrollDragDistanceVertical = layoutHeightPx - itemSizePx

    val overScrollDistanceHorizontal = layoutWidthPx / 2 - halfItemSizePx
    val overScrollDistanceVertical = layoutHeightPx / 2 - halfItemSizePx

    val overScrollDragRangeVertical =
        (-maxOffsetVertical.toFloat() - overScrollDragDistanceVertical)
            .rangeTo(overScrollDragDistanceVertical.toFloat())
val overScrollDragRangeHorizontal =
    (-maxOffsetHorizontal.toFloat() - overScrollDragDistanceHorizontal)
            .rangeTo(overScrollDragDistanceHorizontal.toFloat())

    val overScrollRangeVertical =
        (-maxOffsetVertical.toFloat() - overScrollDistanceVertical)
            .rangeTo(overScrollDistanceVertical.toFloat())
    val overScrollRangeHorizontal =
        (-maxOffsetHorizontal.toFloat() - overScrollDistanceHorizontal) // [-800, 0]
            .rangeTo(overScrollDistanceHorizontal.toFloat())
}