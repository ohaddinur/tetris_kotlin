package com.ohaddinur.tetris.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.ohaddinur.tetris.data.Cell
import com.ohaddinur.tetris.data.CellType
import com.ohaddinur.tetris.data.Position
import java.util.*
import kotlin.concurrent.schedule

@Composable
fun BoardView(cells: List<List<Cell>>,
              cellSize: Int = 20,
              rowsToRemove: List<Int>,
              onTap: ( position: Position) -> Unit,
              onDrag: (position: Position, offset: Position) -> Unit) {
    var animate by rememberSaveable{mutableStateOf(false)}
    val topPadding  by animateDpAsState(
        if (animate) (cellSize * rowsToRemove.size).dp else 0.dp,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow
        )
    )
    val animatedHeight by animateDpAsState(
            if (animate) 0.dp else cellSize.dp,
            animationSpec = spring(
                stiffness = Spring.StiffnessLow
            )
        )

    animate = false;
    Timer("startAnimation", false)
        .schedule(delay = 500.toLong()){
            if(rowsToRemove.isNotEmpty()){
                println(rowsToRemove)
                animate = true
            }
        }
    if(rowsToRemove.isNotEmpty())
        Box(modifier = Modifier
            .height(topPadding)
            .fillMaxWidth())
    Column {
        cells.forEachIndexed { y, row ->
            if(rowsToRemove.contains(y))
                Box(modifier = Modifier
                    .height(animatedHeight)
                    .fillMaxWidth()){}
            else
                Row{
                    row.forEachIndexed { x, cell ->
                        Box(modifier = Modifier.pointerInput(Unit) {
                            detectDragGestures { _, dragAmount ->
                                println(dragAmount)
                                if(dragAmount.x > cellSize)
                                    onDrag(Position(x, y), Position(1,0))
                                if(dragAmount.x < cellSize * -1)
                                    onDrag(Position(x, y), Position(-1,0))
                                if(dragAmount.y > cellSize)
                                    onDrag(Position(x, y), Position(0,1))
                                println(dragAmount)}
                        }
                            .pointerInput(Unit) {
                            detectTapGestures {
                                onTap(Position(x, y))
                            }
                        }) {
                            BoardCell(
                                cell = cell,
                                size = cellSize
                            )
                        }
                    }
                }
        }
    }
}
