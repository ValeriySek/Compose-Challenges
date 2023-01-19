package ru.ozh.compose.challenges.ui.tryy

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

interface TryGridState {

    val currentOffset: Offset
    val animatable: Animatable<Offset, AnimationVector2D>
    var config: TryGridConfig

    val onGesture: (centroid: Offset, pan: Offset, zoom: Float) -> Unit

    suspend fun snapTo(offset: Offset)
    suspend fun animateTo(offset: Offset, velocity: Offset)
    suspend fun stop()

    fun getPositionFor(index: Int): IntOffset
    fun setup(config: TryGridConfig) {
        this.config = config
    }
}