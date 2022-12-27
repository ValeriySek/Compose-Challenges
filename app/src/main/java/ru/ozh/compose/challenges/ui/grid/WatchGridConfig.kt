package ru.ozh.compose.challenges.ui.grid

import android.util.Log
import androidx.compose.ui.unit.IntOffset

class WatchGridConfig(
    val itemSizePx: Int = 0,
    val layoutHeightPx: Int = 0,
    val layoutWidthPx: Int = 0,
    val cells: List<Cell> = emptyList()
) {

    val a = 3f * layoutWidthPx
    val b = 3f * layoutHeightPx
    val c = 20.0f

    val layoutCenter = IntOffset(
        x = layoutWidthPx / 2,
        y = layoutHeightPx / 2
    )

    val halfItemSizePx = itemSizePx / 2

    fun contentHeight(scale: Float) = ((cells.maxByOrNull { it.y }?.y?.let { y -> y + 1 }) ?: 0).times(itemSizePx * scale)
    fun contentWidth(scale: Float) = ((cells.maxByOrNull { it.x }?.x?.let { x -> x + 1 }) ?: 0).times(itemSizePx * scale)

    fun maxOffsetHorizontal(scale: Float) = contentWidth(scale) - layoutWidthPx
    fun maxOffsetVertical(scale: Float) = contentHeight(scale) - layoutHeightPx

    val overScrollDragDistanceHorizontal = layoutWidthPx - itemSizePx
    val overScrollDragDistanceVertical = layoutHeightPx - itemSizePx

    val overScrollDistanceHorizontal = 0
    val overScrollDistanceVertical = 0

    fun overScrollDragRangeVertical(scale: Float) =
        (-maxOffsetVertical(scale) - overScrollDragDistanceVertical)
            .rangeTo(overScrollDragDistanceVertical.toFloat())
    fun overScrollDragRangeHorizontal(scale: Float) =
        (-maxOffsetHorizontal(scale) - overScrollDragDistanceHorizontal)
            .rangeTo(overScrollDragDistanceHorizontal.toFloat())

    fun overScrollRangeVertical(scale: Float) =
        (-maxOffsetVertical(scale) - overScrollDistanceVertical)
            .rangeTo(overScrollDistanceVertical.toFloat())
    fun overScrollRangeHorizontal(scale: Float) =
        (-maxOffsetHorizontal(scale) - overScrollDistanceHorizontal) // [-800, 0]
            .rangeTo(overScrollDistanceHorizontal.toFloat())
}