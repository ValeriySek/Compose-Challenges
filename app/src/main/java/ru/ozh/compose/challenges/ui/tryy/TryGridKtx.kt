package ru.ozh.compose.challenges.ui.tryy

import android.util.Log
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.input.pointer.util.addPointerInputChange
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun Modifier.drag(state: TryGridState) = pointerInput(Unit) {
    detectGestures(
        state = state,
    )
}


suspend fun PointerInputScope.detectGestures(
    state: TryGridState
): Unit = coroutineScope {
//    launch {
//        detectTransformGestures(
//            onGesture = { centroid, pan, zoom ->
//                launch {
//                    state.onGesture(centroid, pan, zoom)
//                }
//            }
//        )
//    }
    launch {
        detectDragGestures(
            state = state
        )
    }
}


private suspend fun PointerInputScope.detectDragGestures(
    state: TryGridState
) {

    val decay = splineBasedDecay<Offset>(this)
    val tracker = VelocityTracker()
    coroutineScope {

        forEachGesture {
            awaitPointerEventScope {
                val pointerId = awaitFirstDown(requireUnconsumed = false).id
                launch {
                    state.stop()
                }
                tracker.resetTracking()
                var dragPointerInput: PointerInputChange?
                var overSlop = Offset.Zero
                do {
                    dragPointerInput = awaitTouchSlopOrCancellation(
                        pointerId
                    ) { change, over ->
                        change.consumePositionChange()
                        overSlop = over
                    }
                    Log.i(
                        "TAGG",
                        "do dragPointerInput $dragPointerInput  dragPointerInput.positionChangeConsumed() ${dragPointerInput?.positionChangeConsumed()}"
                    )
                } while (dragPointerInput != null && !dragPointerInput.positionChangeConsumed())
                Log.i("TAGG", " after do")

                dragPointerInput?.let {

                launch {
                    state.snapTo(state.currentOffset.plus(overSlop))
                }

                    drag(dragPointerInput.id) { change ->
                        val dragAmount = change.positionChange()
                        launch {
                            state.snapTo(state.currentOffset.plus(dragAmount))
                        }
                        change.consumePositionChange()
                        tracker.addPointerInputChange(change)
                    }
                }
            }

            val (velX, velY) = tracker.calculateVelocity()
            val velocity = Offset(velX, velY)
            val targetOffset = decay.calculateTargetValue(
                typeConverter = Offset.VectorConverter,
                initialValue = state.currentOffset,
                initialVelocity = velocity
            )
            launch {
                state.animateTo(
                    offset = targetOffset,
                    velocity = velocity,
                )
            }
        }
    }
}


/**
 * Simplified version of [androidx.compose.foundation.gestures.detectTransformGestures] which
 * awaits two pointer downs (instead of one) and starts immediately without considering touch slop.
 */
private suspend fun PointerInputScope.detectTransformGestures(
    onGesture: (centroid: Offset, pan: Offset, zoom: Float) -> Unit
) {
    forEachGesture {
        awaitPointerEventScope {
            awaitTwoDowns(false)
            do {
                val event = awaitPointerEvent()
                val canceled = event.changes.any { it.positionChangeConsumed() }
                if (!canceled) {
                    val zoomChange = event.calculateZoom()
                    val panChange = event.calculatePan()
                    val centroid = event.calculateCentroid(useCurrent = false)
                    if (zoomChange != 1f || panChange != Offset.Zero) {
                        onGesture(centroid, panChange, zoomChange)
                    }
                    event.changes.forEach {
                        if (it.positionChanged()) {
                            it.consumeAllChanges()
                        }
                    }
                }
            } while (!canceled && event.changes.any { it.pressed })
        }
    }
}

private suspend fun AwaitPointerEventScope.awaitTwoDowns(requireUnconsumed: Boolean = true) {
    var event: PointerEvent
    var firstDown: PointerId? = null
    do {
        event = awaitPointerEvent()
        var downPointers = if (firstDown != null) 1 else 0
        event.changes.forEach {
            val isDown =
                if (requireUnconsumed) it.changedToDown() else it.changedToDownIgnoreConsumed()
            val isUp =
                if (requireUnconsumed) it.changedToUp() else it.changedToUpIgnoreConsumed()
            if (isUp && firstDown == it.id) {
                firstDown = null
                downPointers -= 1
            }
            if (isDown) {
                firstDown = it.id
                downPointers += 1
            }
        }
        val satisfied = downPointers > 1
    } while (!satisfied)
}