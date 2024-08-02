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
    fun testCanMove() {
        //Create a sample board state
        val boardState = BoardState(4, 4)
        boardState.setCells(
            listOf(
                Position(0, 0), Position(1, 0), Position(2, 0), Position(3, 0),
                Position(0, 1), Position(1, 1),
                Position(0, 2), Position(1, 2), Position(2, 2), Position(3, 2),
                Position(0, 3), Position(1, 3), Position(2, 3), Position(3, 3)
            ), Cell(CellType.Square, Color.Red)
        )

        //Test moving within bounds
        val canMoveWithinBounds = boardState.canMove(
            listOf(Position(0, 1), Position(1, 1)),
            1,
            0
        )
        assertTrue(canMoveWithinBounds)

        //Test moving out of bounds (right)
        val canMoveOutOfBoundsRight = boardState.canMove(
            listOf(Position(2, 1), Position(3, 1), Position(4, 1)),
            1,
            0
        )
        assertFalse(canMoveOutOfBoundsRight)

        //Test moving out of bounds (left)
        val canMoveOutOfBoundsLeft = boardState.canMove(
            listOf(Position(0, 1), Position(-1, 1), Position(-2, 1)),
            -1,
            0
        )
        assertFalse(canMoveOutOfBoundsLeft)

        //Test moving out of bounds (down)
        val canMoveOutOfBoundsDown = boardState.canMove(
            listOf(Position(1, 3), Position(2, 3), Position(3, 3)),
            0,
            1
        )
        assertFalse(canMoveOutOfBoundsDown)

        //Test moving to a cell that is not empty
        val canMoveToNonEmptyCell = boardState.canMove(
            listOf(Position(1, 1), Position(2, 1), Position(3, 1)),
            0,
            1
        )
        assertFalse(canMoveToNonEmptyCell)
    }

    @Test
    fun testFullRows() {
        //Create a sample board state
        val boardState = BoardState(4, 4)
        boardState.setCells(listOf(
            Position(0, 0), Position(1, 0), Position(2, 0), Position(3, 0),
            Position(0, 1), Position(2, 1), Position(3, 1),
            Position(0, 2), Position(1, 2), Position(2, 2), Position(3, 2),
            Position(0, 3), Position(1, 3), Position(2, 3), Position(3, 3)
        ), Cell(CellType.Square, Color.Red)
        )

        //Call the function under test
        val removedRows = boardState.fullRows()

        //Assert that all full rows are removed
        assertEquals(listOf(0, 2, 3), removedRows)
        //Assert that list remain same size
        assertEquals(4, boardState.cells.size)
        //assertEquals(3, (boardState.cells.find {  row -> row == MutableList(4) { Cell()  }})?.size)
    }

    @Test
    fun testSetCells() {
        // Create a sample board state
        val boardState = BoardState(4, 4)

        // Set cells at specific positions
        boardState.setCells(
            listOf(
                Position(1, 1),
                Position(2, 2),
                Position(3, 3)
            ),
            Cell(CellType.Square, Color.Red)
        )

        // Assert that the cells are set correctly
        assertEquals(Cell(CellType.Square, Color.Red), boardState.cells[1][1])
        assertEquals(Cell(CellType.Square, Color.Red), boardState.cells[2][2])
        assertEquals(Cell(CellType.Square, Color.Red), boardState.cells[3][3])
    }

    @Test
    fun testUpdateCells() {
        // Create a sample board state
        val boardState = BoardState(4, 4)
        boardState.setCells(
            listOf(
                Position(1, 1),
                Position(2, 2),
                Position(3, 3)
            ),
            Cell(CellType.Square, Color.Red)
        )

        // Update cells by removing and adding positions
        boardState.updateCells(
            listOf(Position(1, 1), Position(2, 2)),
            listOf(Position(0, 0), Position(3, 3)),
            Cell(CellType.Square, Color.Blue)
        )

        // Assert that the cells are updated correctly
        assertEquals(Cell(), boardState.cells[1][1])
        assertEquals(Cell(), boardState.cells[2][2])
        assertEquals(Cell(CellType.Square, Color.Blue), boardState.cells[0][0])
        assertEquals(Cell(CellType.Square, Color.Blue), boardState.cells[3][3])
    }

    // Add more tests as needed...

}