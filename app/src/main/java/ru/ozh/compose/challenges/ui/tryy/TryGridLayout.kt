package ru.ozh.compose.challenges.ui.tryy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import ru.ozh.compose.challenges.ui.grid.*
import ru.ozh.compose.challenges.ui.table.data.Elements.elements

@Composable
fun TryGridLayout(
    modifier: Modifier,
    itemSize: Dp,
    state: TryGridState = rememberTryGridState(),
    content: @Composable () -> Unit
    ){

    val itemSizePx = with(LocalDensity.current) { itemSize.roundToPx() }

    Layout(
        modifier = modifier.drag(state),
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map { it.measure(constraints) }
        val cells = elements.map {
            Cell(it.group - 1, it.period - 1)
        }.toList()

        state.setup(
            TryGridConfig(
                itemSizePx = itemSizePx,
                layoutHeightPx = constraints.maxHeight,
                layoutWidthPx = constraints.maxWidth,
                cells = listOf(Cell(0,0))
            )
        )

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEachIndexed { index, placeable ->
                val position = state.getPositionFor(index)
                placeable.place(
                    position = position
                )
            }
        }
    }

}