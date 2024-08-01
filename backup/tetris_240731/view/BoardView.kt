package com.ohaddinur.tetris.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ohaddinur.tetris.data.Cell
import com.ohaddinur.tetris.data.CellType

@Composable
fun BoardView(cells: List<List<Cell>>, cellSize: Int = 20) {
    Column {
        cells.forEachIndexed { y, row ->
            Row {
                row.forEachIndexed { x, cell ->
                    BoardCell(
                        cell = cell,
                        size = cellSize
                    )
                }
            }
        }
    }
}
