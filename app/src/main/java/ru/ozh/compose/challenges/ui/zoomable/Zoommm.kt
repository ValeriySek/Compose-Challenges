package ru.ozh.compose.challenges.ui.zoomable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlin.math.roundToInt

@Composable
fun TransformableSample() {
    // set up all transformation states
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    val size = 30 * scale

    ConstraintLayout {
    }


    Column(
        Modifier
            // apply other transformations like rotation and zoom
            // on the pizza slice emoji
//            .graphicsLayer(
//                scaleX = scale,
//                scaleY = scale,
//                rotationZ = rotation,
//                translationX = offset.x,
//                translationY = offset.y
//            )
            .layout { measurable, constraints ->
                val width = constraints.maxWidth
                val height = constraints.maxHeight
                val placeable = measurable.measure(
                    Constraints(
                        maxWidth = (width * scale).roundToInt(),
                        maxHeight = (height * scale).roundToInt()
                    )
                )
                layout(width, height) {
                    placeable.placeWithLayer(
                        offset.x.roundToInt(),
                        offset.y.roundToInt()
                    )
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offset += Offset(dragAmount.x, dragAmount.y)
                }
            }
            // add transformable to listen to multitouch transformation events
            // after offset
            .transformable(state = state)
            .fillMaxSize()
    ){
        Row {

            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
        }
        Row {

            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
        }
        Row {

            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text", fontSize = size.sp)
            }
            Box(
                Modifier
                    .background(Color.Red)
                    .fillMaxWidth()
                    .weight(1f)
            ){

                Text(text = "Text2", fontSize = 30.sp)
            }
        }
    }
}