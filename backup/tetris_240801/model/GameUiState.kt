package com.ohaddinur.tetris.model

import com.ohaddinur.tetris.data.*

data class GameUiState (
    val score: Int = 0,
    val gameOver: Boolean = false,
    val boardCells: List<List<Cell>> = mutableListOf<List<Cell>>(),
    val rowsToRemove: List<Int> = mutableListOf<Int>(),
    val comparable: Int = 0
)

