package com.ohaddinur.tetris.view

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.ohaddinur.tetris.data.Cell
import com.ohaddinur.tetris.data.Position

@Composable
fun BoardView(cells: List<List<Cell>>,
              cellSize: Int,
              rowsToRemove: List<Int>,
              onTap: ( position: Position) -> Unit,
              onAccelerate: (position: Position) -> Unit,
              onDragX: (direction: Int, position: Position?) -> Unit) {

    var draggging: Boolean = remember {false}

    Column {
        cells.forEachIndexed { y, row ->
                Row{
                    (if(rowsToRemove.contains(y))
                        MutableList(row.size) { Cell() }
                    else
                    row).forEachIndexed { x, cell ->
                        Box(modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    onTap(Position(x, y))
                                }
                            }
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragEnd = {
                                        draggging = false
                                        onDragX(0, null)
                                                },
                                    onDragCancel = {
                                        draggging = false
                                        onDragX(0, null)
                                       },
                                ) { change, dragAmount ->
                                    change.consume()
                                    if(!draggging){
                                        var dragPosition = Position(x,y)
                                        draggging = true
                                        if(dragAmount.x > 2)
                                            Thread {onDragX(1, dragPosition)}.start()
                                        else if(dragAmount.x < -2)
                                            Thread { onDragX(-1, dragPosition) }.start()
                                        else if(dragAmount.y > 2)
                                            onAccelerate(dragPosition!!)
                                    }
                                }
                            }
                        ) {
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
