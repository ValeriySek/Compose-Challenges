package ru.ozh.compose.challenges.ui.grid

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

data class WatchGridStateImpl(
    private val initialOffset: Offset = Offset.Zero
) : WatchGridState {

    companion object {
        val Saver = Saver<WatchGridStateImpl, List<Float>>(
            save = {
                val (x, y) = it.currentOffset
                listOf(x, y)
            },
            restore = {
                WatchGridStateImpl(initialOffset = Offset(it[0], it[1]))
            }
        )
    }

    private val decayAnimationSpec = SpringSpec<Offset>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
    )

    override val animatable = Animatable(
        initialValue = initialOffset,
        typeConverter = Offset.VectorConverter
    )

    override var config: WatchGridConfig = WatchGridConfig()

    override val currentOffset: Offset
        get() = animatable.value

    override suspend fun snapTo(offset: Offset) {
//        val scaledX = config.overScrollDragRangeHorizontal.
        val x = offset.x.coerceIn(config.overScrollDragRangeHorizontal)
        val y = offset.y.coerceIn(config.overScrollDragRangeVertical)
        animatable.snapTo(Offset(x, y))
    }

    override suspend fun animateTo(offset: Offset, velocity: Offset) {
        Log.i("TAGGG", "config.overScrollRangeHorizontal ${config.overScrollRangeHorizontal}")
        val x = offset.x.coerceIn(config.overScrollRangeHorizontal)
        val y = offset.y.coerceIn(config.overScrollRangeVertical)
        animatable.animateTo(
            initialVelocity = velocity,
            animationSpec = decayAnimationSpec,
            targetValue = Offset(x, y)
        )
    }

    override suspend fun stop() {
        animatable.stop()
    }

    override fun getPositionFor(index: Int): IntOffset {
        val (offsetX, offsetY) = currentOffset
        val (cellX, cellY) = config.cells[index]
        val x = (cellX * (config.itemSizePx * scale)).toInt() + offsetX.toInt() + (config.itemSizePx * (scale - 1) / 2).toInt() + config.itemSizePx / 2
        val y = (cellY * (config.itemSizePx * scale)).toInt() + offsetY.toInt() + (config.itemSizePx * (scale - 1) / 2).toInt() + config.itemSizePx / 2
        if (index == 0)
            Log.i(
                "TAGG",
                "x $x y $y "
            )

        return IntOffset(x, y)
    }

    override fun getScaleFor(position: IntOffset): Float {
        val (centerX, centerY) = position.plus(
            IntOffset(
                config.halfItemSizePx,
                config.halfItemSizePx
            )
        )
        val offsetX = centerX - config.layoutCenter.x
        val offsetY = centerY - config.layoutCenter.y
        val x = (offsetX * offsetX) / (config.a * config.a)
        val y = (offsetY * offsetY) / (config.b * config.b)
        val z = (-config.c * (x + y) + 1.1f)
            .coerceIn(minimumValue = 0.5f, maximumValue = 1f)
        return z
    }

    private var _scale by mutableStateOf(ZoomableDefaults.MinScale)

    override var scale: Float
        get() = _scale
        set(value) {
            _scale = value.coerceIn(
                minimumValue = ZoomableDefaults.MinScale,
                maximumValue = ZoomableDefaults.MaxScale
            )
//            config = config.copy(itemSizePx = (config.itemSizePx * value).toInt())
//            Log.i("TAGGG", "config.overScrollRangeHorizontal ${config.overScrollRangeHorizontal}")
        }

    override val onGesture: (centroid: Offset, pan: Offset, zoom: Float) -> Unit = { centroid, pan, zoom ->
        Log.i("TAGG", "zoom $zoom ")
        scale *= zoom
    }


//    private fun updateBounds() {
//        val offsetX = childSize.width * scale - size.width
//        val offsetY = childSize.height * scale - size.height
//        boundOffset = IntOffset((offsetX / 2f).roundToInt(), (offsetY / 2f).roundToInt())
//        val maxX = offsetX.coerceAtLeast(0f) / 2f
//        val maxY = offsetY.coerceAtLeast(0f) / 2f
//        _translationX.updateBounds(-maxX, maxX)
//        _translationY.updateBounds(-maxY, maxY)
//    }
}

object ZoomableDefaults {

    const val MinScale = 1f

    const val MaxScale = 2f
}

@Composable
fun rememberWatchGridState(): WatchGridState {
    return rememberSaveable(saver = WatchGridStateImpl.Saver) {
        WatchGridStateImpl()
    }
}