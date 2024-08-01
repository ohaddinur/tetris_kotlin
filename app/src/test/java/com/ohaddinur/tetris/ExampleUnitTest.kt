package com.ohaddinur.tetris

import org.junit.Test

import org.junit.Assert.*

import androidx.compose.ui.graphics.Color
import com.ohaddinur.tetris.data.BoardState
import com.ohaddinur.tetris.data.Cell
import com.ohaddinur.tetris.data.CellType
import com.ohaddinur.tetris.data.Position

class BoardStateTest {
    @Test
    fun testFullRows() {
        // Create a sample board state
        val boardState = BoardState(4, 4)
        boardState.setCells(listOf(
            Position(0, 0), Position(1, 0), Position(2, 0), Position(3, 0),
            Position(0, 1), Position(1, 1), Position(2, 1), Position(3, 1),
            Position(0, 2), Position(1, 2), Position(2, 2), Position(3, 2),
            Position(0, 3), Position(1, 3), Position(2, 3), Position(3, 3)
        ), Cell(CellType.Square, Color.Red)
        )

        // Call the function under test
        val removedRows = boardState.fullRows()

        // Assert that all full rows are removed
        assertEquals(listOf(3, 2, 1, 0), removedRows)
        assertEquals(MutableList(4) { MutableList(4) { Cell() } }, boardState.cells)
    }
}