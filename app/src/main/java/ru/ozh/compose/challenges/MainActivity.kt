package ru.ozh.compose.challenges

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.ozh.compose.challenges.ui.grid.*
import ru.ozh.compose.challenges.ui.grid.Icons.appleIcons
import ru.ozh.compose.challenges.ui.table.TableGridLayout
import ru.ozh.compose.challenges.ui.table.data.Elements.elements
import ru.ozh.compose.challenges.ui.theme.ComposeChallengesTheme
import ru.ozh.compose.challenges.ui.tryy.TryGridConfig
import ru.ozh.compose.challenges.ui.tryy.TryGridLayout
import ru.ozh.compose.challenges.ui.tryy.drag
import ru.ozh.compose.challenges.ui.tryy.rememberTryGridState
import ru.ozh.compose.challenges.ui.zoomable.TransformableSample

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GridContent()
        }
    }

    @Composable
    private fun GridContent() {

        var toast: Toast? = null

        ComposeChallengesTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
//                WatchGridLayout(
//                    modifier = Modifier
//                        .background(
//                            color = Color.Black
//                        )
//                    ,
//                    rowItemsCount = 10,
//                    itemSize = 80.dp
//                ) {
//                    appleIcons.forEach { (res, name) ->
//                        Icon(res = res,
//                            modifier = Modifier.clickable {
//                                toast?.cancel()
//                                toast = Toast.makeText(
//                                    this@MainActivity,
//                                    "You've clicked on $name",
//                                    Toast.LENGTH_SHORT
//                                )
//
//                                toast?.show()
//                            }
//                        )
//                    }
//                }


//                TransformableSample()


//                TryGridLayout(modifier = Modifier
//                    .background(color = Color(0xFF673AB7)), itemSize = 1000.dp) {
//                    Box(
//                        modifier = Modifier
//                            .padding(5.dp)
//                            .background(color = Color(0xFF457345))
//                    ) {
//                        Text(text = "kjnsidb")
//                    }
//                }
//                Row(modifier = Modifier
//                    .wrapContentWidth()
//                    .background(Color.Blue)) {
//                    Column(modifier = Modifier
//                        .height(1000.dp)
//                        .background(Color.Green)) {
//                        repeat(10) {
//                            Box(
//                                modifier = Modifier
//                                    .size(95.dp)
//                                    .padding(2.dp)
//                                    .background(Color.Yellow),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text("1")
//                            }
//                        }
//                    }
//                }

//                MyColumnLayout {
//                    repeat(4) {
//                        Box(
//                            modifier = Modifier
//                                .size(95.dp)
//                                .padding(2.dp)
//                                .background(Color.Yellow),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text("1")
//                        }
//                    }
//                    repeat(3) {
//                        Text("2")
//                    }
//                }


                TableGridLayout(modifier = Modifier, itemSize = 100.dp) {
                    elements.forEach {
                        Item(
                            element = it
                        ) {

                            toast?.cancel()
                            toast = Toast.makeText(
                                this@MainActivity,
                                "You've clicked on ${it.symbol}",
                                Toast.LENGTH_SHORT
                            )

                            toast?.show()

                        }
                    }
//                    Column {
//                        repeat(10) {
//                            Box(
//                                modifier = Modifier
//                                .size(50.dp, 100.dp)
//                                .background(Color.Blue)
//                            )
//                        }
//                    }
//                    Row {
//                        repeat(18) {
//                            Box(
//                                modifier = Modifier
//                                    .size(100.dp, 50.dp)
//                                    .background(Color.Green)
//                            )
//                        }
//                    }
                }
            }
        }
    }
}



@Composable
fun MyColumnLayout(children: @Composable () -> Unit) {
    Layout(children) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }

        val maxWidth = placeables.maxOfOrNull { it.width } ?: 0
        val height = placeables.sumBy { it.height }

        layout(maxWidth, height) {
            var currentHeight = 0
            placeables.forEach {
                it.place(0, currentHeight)
                currentHeight += it.height
            }
        }
    }
}
