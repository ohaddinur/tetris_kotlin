package com.ohaddinur.tetris.model

import androidx.lifecycle.ViewModel
import com.ohaddinur.tetris.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Timer
import kotlin.concurrent.schedule


data class GameViewModel (val rows: Int, val columns: Int) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var _score: Int = 0
    private var _gameOver: Boolean = false
    private lateinit var _board: BoardState
    private var _speed: Int = 1000
    private lateinit var _shape: Shape
    private var comparable: Int = 0;

    init {
        resetGame()
        tick()
    }

    private fun getNewShape() {
        _shape = Shape.random()
        _shape.offset = Position(5, 0)
        _board.setCells(
            _shape.getPositions(), Cell(CellType.Square,  _shape.color)
        )
    }

    fun resetGame() {
        _board = BoardState(rows, columns)
        _speed = 1000
        _score = 0
        _gameOver = false
        _uiState.value = GameUiState(
            score = _score,
            gameOver = _gameOver,
            boardCells = _board.cells,
            comparable = comparable++
            )

        getNewShape()
        tick()
    }

   private fun tick() {
       println("tick")
        if (!moveShape(0, 1)) {
            val fullRows = _board.rowFull()
            if (fullRows > 0) {
                _score += 10 * fullRows
                _uiState.update { currentState ->
                    currentState.copy(score = _score)
                }
                }
                if (_speed > 500) _speed -= 50
            if (_shape.offset.y > 0) {
                getNewShape()
            } else {
                _gameOver = true
                _uiState.update { currentState ->
                    currentState.copy(gameOver = _gameOver)
                }
            }
        }
        if (!_gameOver)
            Timer("nextTick", false).schedule(delay = _speed.toLong()){
                tick()
            }
    }

    fun moveShape(dx: Int, dy: Int): Boolean {
        println("moveShape: $dx, $dy")
        if (!_board.canMove(_shape.getPositions(), dx, dy)) {

            print("can't move")
            return false
        }

        val positionsToRemove = _shape.getPositions()
        _shape.move(dx, dy)
        _board.updateCells(
            positionsToRemove,
            _shape.getPositions(),
            Cell(CellType.Square, _shape.color)
        )
        _uiState.update { currentState ->
            currentState.copy(boardCells = _board.cells,
                comparable = comparable++)
        }
        return true
    }

    fun rotateShape(){
        val positionsToRemove = _shape.getPositions()
        _shape.rotate()
        _board.updateCells(positionsToRemove, _shape.getPositions(), Cell(CellType.Square, _shape.color))

        _uiState.update { currentState ->
            currentState.copy(boardCells = _board.cells,
                comparable = comparable++)
        }
    }
}