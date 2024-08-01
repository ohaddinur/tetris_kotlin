package com.ohaddinur.tetris.view

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import com.ohaddinur.tetris.data.Cell
import com.ohaddinur.tetris.data.Position

@Composable
fun BoardView(cells: List<List<Cell>>,
              cellSize: Int,
              scale: Float,
              rowsToRemove: List<Int>,
              onTap: ( position: Position) -> Unit,
              onDrag: (position: Position, offset: Offset) -> Unit) {

    Column {
        cells.forEachIndexed { y, row ->
                Row{
                    (if(rowsToRemove.contains(y))
                        MutableList(row.size) { Cell() }
                    else
                    row).forEachIndexed { x, cell ->
                        Box(modifier = Modifier
                            .pointerInput(Unit) {
                                detectDragGestures { _, dragAmount ->
                                    var dy = 0F
                                    if(dragAmount.y > 1)
                                        dy = dragAmount.y / (cellSize / scale)

                                    onDrag(Position(x, y), Offset(dragAmount.x / (cellSize / scale),dy))
                                }
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
