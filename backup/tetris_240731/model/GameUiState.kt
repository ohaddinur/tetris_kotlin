package com.ohaddinur.tetris.model

import com.ohaddinur.tetris.data.*

data class GameUiState (
    val score: Int = 0,
    val gameOver: Boolean = false,
    val boardCells: List<List<Cell>> = MutableList(0) { MutableList(0) { Cell() } },
    val comparable: Int = 0
)

