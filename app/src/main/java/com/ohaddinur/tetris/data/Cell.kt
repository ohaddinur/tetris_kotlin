package com.ohaddinur.tetris.data

import androidx.compose.ui.graphics.Color

data class Cell(val type: CellType = CellType.None, val color: Color = Color.Transparent)

enum class CellType {
    None, Square
}