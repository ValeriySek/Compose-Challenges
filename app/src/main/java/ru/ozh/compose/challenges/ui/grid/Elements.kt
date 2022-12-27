package ru.ozh.compose.challenges.ui.grid

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Icon(
    modifier: Modifier = Modifier,
    @DrawableRes res: Int
) {
    BoxWithConstraints(
        modifier = Modifier
            .clip(CircleShape)
            .layout { measurable, constraints ->
                val c = measurable.measure(constraints)
                layout(constraints.maxWidth, constraints.maxHeight) {
                    c.measuredWidth
                    Log.i("TAGGGG", "c.measuredWidth ${c.measuredWidth}")
                }
            }
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = res),
            contentDescription = ""
        )
    }
}

@Composable
fun Item(
    modifier: Modifier = Modifier,
    label: String = ""
) {
    Box(
        modifier = modifier
            .padding(2.dp)
            .background(color = Color(0xFFEEEEEE)),
        contentAlignment = Alignment.Center

    ) {

        Text(
            text = label,
//            fontSize = 40.sp,
            textAlign = TextAlign.Center)

    }
}

@Composable
fun CrossLine() {
    Canvas(
        modifier = Modifier
            .fillMaxSize(),
        onDraw = {
            drawLine(
                start = Offset(this.size.width / 2, 0f),
                end = Offset(this.size.width / 2, this.size.height),
                color = Color.White,
                strokeWidth = 4f
            )

            drawLine(
                start = Offset(0f, this.size.height / 2),
                end = Offset(this.size.width, this.size.height / 2),
                color = Color.White,
                strokeWidth = 4f
            )
        }
    )
}