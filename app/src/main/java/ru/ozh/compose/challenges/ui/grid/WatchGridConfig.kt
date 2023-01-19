package ru.ozh.compose.challenges.ui.grid

import android.util.Log
import androidx.compose.ui.unit.IntOffset
import kotlin.math.min

data class WatchGridConfig(
    var itemSizePx: Int = 0, // 275
    val layoutHeightPx: Int = 0, // 2204
    val layoutWidthPx: Int = 0, // 1080
    val cells: List<Cell> = emptyList()
) {

    val a = 3f * layoutWidthPx
    val b = 3f * layoutHeightPx
    val c = 20.0f
    val offset = itemSizePx / 2

    val layoutCenter = IntOffset(
        x = layoutWidthPx / 2,
        y = layoutHeightPx / 2
    )

    val halfItemSizePx = itemSizePx / 2

    val contentWidth = ((cells.maxByOrNull { it.x }?.x?.let { x -> x + 1 }) ?: 0).times(itemSizePx) + offset // 4950
    val contentHeight = ((cells.maxByOrNull { it.y }?.y?.let { y -> y + 1 }) ?: 0).times(itemSizePx) + offset // 1925

    val maxOffsetHorizontal = contentWidth - layoutWidthPx // 3870
    val maxOffsetVertical = contentHeight - layoutHeightPx // -279

    val overScrollDragDistanceHorizontal = layoutWidthPx - itemSizePx // 805
    val overScrollDragDistanceVertical = layoutHeightPx - itemSizePx // 1929

    val overScrollDistanceHorizontal = 0
    val overScrollDistanceVertical = 0

    val overScrollDragRangeHorizontal =
        (-maxOffsetHorizontal.toFloat() - overScrollDragDistanceHorizontal) // -4675.0..805.0
            .rangeTo(overScrollDragDistanceHorizontal.toFloat())
    val overScrollDragRangeVertical =
        (-maxOffsetVertical.toFloat() - overScrollDragDistanceVertical) // -1650.0..1929.0
            .rangeTo(overScrollDragDistanceVertical.toFloat())

    val overScrollRangeHorizontal =
        (-maxOffsetHorizontal.toFloat() - overScrollDistanceHorizontal) // -4273.0..403.0 // -3870.0..0.0
            .rangeTo(overScrollDistanceHorizontal.toFloat())
    val overScrollRangeVertical =
        (min(-maxOffsetVertical.toFloat(), 0f) - overScrollDistanceVertical) // -686.0..965.0
            .rangeTo(overScrollDistanceVertical.toFloat())
}