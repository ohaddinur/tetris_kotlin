package com.ohaddinur.tetris.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ohaddinur.tetris.data.Cell
import com.ohaddinur.tetris.data.CellType

@Composable
fun BoardCell(cell: Cell, size: Int) {
    // Implement the UI for a single cell here
    // Example:
    Box(Modifier
        .size(size.dp)
        .clip(RoundedCornerShape(if(cell.type == CellType.None) 0.dp else 5.dp))
        .border(width = if(cell.type == CellType.None) 0.5.dp else 2.dp,
            color = if(cell.type == CellType.None) Color.Gray.copy(alpha = 0.2f) else cell.color,
            shape= RoundedCornerShape(if(cell.type == CellType.None) 0.dp else 5.dp))
        .background(if(cell.type == CellType.None) Color.Transparent else cell.color.copy(alpha = 0.5f))
        )


}