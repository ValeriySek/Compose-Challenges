package ru.ozh.compose.challenges.ui.grid

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

interface WatchGridState {

    val currentOffset: Offset
    val animatable: Animatable<Offset, AnimationVector2D>
    var config: WatchGridConfig
    var scale: Float

    val onGesture: (centroid: Offset, pan: Offset, zoom: Float) -> Unit

    suspend fun snapTo(offset: Offset)
    suspend fun animateTo(offset: Offset, velocity: Offset)
    suspend fun stop()

    fun getPositionFor(index: Int): IntOffset
    fun getScaleFor(position: IntOffset): Float
    fun setup(config: WatchGridConfig) {
        this.config = config
    }
}