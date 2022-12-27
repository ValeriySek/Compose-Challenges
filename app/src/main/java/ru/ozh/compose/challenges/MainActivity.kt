package ru.ozh.compose.challenges

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.ozh.compose.challenges.ui.grid.Icon
import ru.ozh.compose.challenges.ui.grid.Icons.appleIcons
import ru.ozh.compose.challenges.ui.grid.Item
import ru.ozh.compose.challenges.ui.grid.WatchGridLayout
import ru.ozh.compose.challenges.ui.grid.rememberWatchGridState
import ru.ozh.compose.challenges.ui.table.TableGridLayout
import ru.ozh.compose.challenges.ui.table.data.Elements.elements
import ru.ozh.compose.challenges.ui.theme.ComposeChallengesTheme
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
                contentAlignment = Alignment.Center
            ) {
                WatchGridLayout(
                    modifier = Modifier
                        .background(
                            color = Color.Black
                        )
                    ,
                    rowItemsCount = 10,
                    itemSize = 80.dp
                ) {
                    appleIcons.forEach { (res, name) ->
                        Icon(res = res,
                            modifier = Modifier.clickable {
                                toast?.cancel()
                                toast = Toast.makeText(
                                    this@MainActivity,
                                    "You've clicked on $name",
                                    Toast.LENGTH_SHORT
                                )

                                toast?.show()
                            }
                        )
                    }
                }



//                TransformableSample()




//                TableGridLayout(modifier = Modifier, itemSize = 100.dp) {
//                    elements.forEach {
//                        Item(
//                            label = it.symbol,
//                            modifier = Modifier.clickable {
//
//                                toast?.cancel()
//                                toast = Toast.makeText(
//                                    this@MainActivity,
//                                    "You've clicked on ${it.symbol}",
//                                    Toast.LENGTH_SHORT
//                                )
//
//                                toast?.show()
//                            }
//                        )
//                    }
//                }
            }
        }
    }
}