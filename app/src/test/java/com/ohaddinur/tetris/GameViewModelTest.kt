package com.ohaddinur.tetris.model

import androidx.compose.ui.graphics.Color
import com.ohaddinur.tetris.data.BoardState
import com.ohaddinur.tetris.data.Cell
import com.ohaddinur.tetris.data.CellType
import com.ohaddinur.tetris.data.Position
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var gameViewModel: GameViewModel

    @Before
    fun setUp() {
        gameViewModel = GameViewModel(4, 4)
    }

    @Test
    fun testResetGame() {
        gameViewModel.resetGame()

        // Assert that the game state is reset
        assertEquals(0, gameViewModel.uiState.value.score)
        println(gameViewModel.uiState.value.gameOver)
        assertFalse(gameViewModel.uiState.value.gameOver)
        assertEquals(BoardState(4, 4), gameViewModel.uiState.value.boardCells)
    }

    @Test
    fun testMoveShape() {
        gameViewModel.resetGame()

        // Move the shape to the right
        val movedRight = gameViewModel.moveShape(1, 0)
        //assertTrue(movedRight)

        // Assert that the shape has moved to the right
        val expectedCellsRight = listOf(
            Position(6, 0), Position(7, 0), Position(8, 0), Position(9, 0)
        )
        val expectedBoardStateRight = BoardState(4, 4)
        expectedBoardStateRight.setCells(
            expectedCellsRight,
            Cell(CellType.Square, Color.Red)
        )
        assertEquals(expectedBoardStateRight, gameViewModel.uiState.value.boardCells)

        // Move the shape to the left
        val movedLeft = gameViewModel.moveShape(-1, 0)
        assertTrue(movedLeft)

        // Assert that the shape has moved to the left
        val expectedCellsLeft = listOf(
            Position(5, 0), Position(6, 0), Position(7, 0), Position(8, 0)
        )
        val expectedBoardStateLeft = BoardState(4, 4)
        expectedBoardStateLeft.setCells(
            expectedCellsLeft,
            Cell(CellType.Square, Color.Red)
        )
        assertEquals(expectedBoardStateLeft, gameViewModel.uiState.value.boardCells)

        // Move the shape down
        val movedDown = gameViewModel.moveShape(0, 1)
        assertTrue(movedDown)

        // Assert that the shape has moved down
        val expectedCellsDown = listOf(
            Position(5, 1), Position(6, 1), Position(7, 1), Position(8, 1)
        )
        val expectedBoardStateDown = BoardState(4, 4)
        expectedBoardStateDown.setCells(
            expectedCellsDown,
            Cell(CellType.Square, Color.Red)
        )
        assertEquals(expectedBoardStateDown, gameViewModel.uiState.value.boardCells)

        // Move the shape to an invalid position
        val movedInvalid = gameViewModel.moveShape(0, 1)
        assertFalse(movedInvalid)

        // Assert that the shape has not moved to an invalid position
        assertEquals(expectedBoardStateDown, gameViewModel.uiState.value.boardCells)
    }

    // Add more tests as needed...

}