package ru.ozh.compose.challenges.ui.tryy

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

data class TryGridStateImpl(
    private val initialOffset: Offset = Offset.Zero
) : TryGridState {

    companion object {
        val Saver = Saver<TryGridStateImpl, List<Float>>(
            save = {
                val (x, y) = it.currentOffset
                listOf(x, y)
            },
            restore = {
                TryGridStateImpl(initialOffset = Offset(it[0], it[1]))
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

    override var config: TryGridConfig = TryGridConfig()

    override val currentOffset: Offset
        get() = animatable.value

    override suspend fun snapTo(offset: Offset) {
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
        val x = (cellX * config.itemSizePx) + offsetX.toInt()
        val y = (cellY * config.itemSizePx) + offsetY.toInt()
        if (index == 0)
            Log.i(
                "TAGG",
                "x $x y $y "
            )

        return IntOffset(x, y)
    }

    override val onGesture: (centroid: Offset, pan: Offset, zoom: Float) -> Unit = { centroid, pan, zoom ->
        Log.i("TAGG", "zoom $zoom ")
    }
}

@Composable
fun rememberTryGridState(): TryGridState {
    return rememberSaveable(saver = TryGridStateImpl.Saver) {
        TryGridStateImpl()
    }
}