package ru.ozh.compose.challenges.ui.table.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.ozh.compose.challenges.ui.grid.Item
import ru.ozh.compose.challenges.ui.table.TableGridLayout
import ru.ozh.compose.challenges.ui.table.data.Elements
import ru.ozh.compose.challenges.ui.table.navigation.Screen

@Composable
fun TableScreen(
    navController: NavController
) {


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {


        val itemSize = 100.dp
        val axisSize = 50.dp

        TableGridLayout(modifier = Modifier, itemSize = itemSize) {
            Elements.elements.forEach {
                Item(
                    modifier = Modifier.layoutId("Item"),
                    element = it
                ) {
                    navController.navigate(Screen.Element.screenName + "/${it.symbol}")
//                toast?.cancel()
//                toast = Toast.makeText(this@MainActivity, "You've clicked on ${it.symbol}", Toast.LENGTH_SHORT)
//                toast?.show()
                }
            }

            (1..10).forEach {
                Box(
                    modifier = Modifier
                        .background(color = Color.White)
                        .layoutId("VerticalAxis")
                        .size(axisSize, itemSize)
                ) {
                    Text(modifier = Modifier.align(alignment = Alignment.Center), text = "$it")
                }
            }

            (1..18).forEach {
                Box(
                    modifier = Modifier
                        .background(color = Color.White)
                        .layoutId("HorizontalAxis")
                        .size(itemSize, axisSize)
                ) {
                    Text(modifier = Modifier.align(alignment = Alignment.Center), text = "$it")
                }
            }

            Box(
                modifier = Modifier
                    .layoutId("Box")
                    .size(axisSize)
                    .background(Color.White)
            )
        }
    }
}