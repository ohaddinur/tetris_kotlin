package com.ohaddinur.tetris.model

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.ohaddinur.tetris.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*
import kotlin.concurrent.schedule


data class GameViewModel (val rows: Int, val columns: Int) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var _score: Int = 0
    private var _gameOver: Boolean = false
    private lateinit var _board: BoardState
    private var _speed: Int = 1000
    private lateinit var _activeShape: Shape
    private var _comparable: Int = 0
    private var _tickTimer: TimerTask? = null
    private var _preventTick = false;

    init {
        resetGame()
    }

    private fun getNewShape() {
        _activeShape = Shape.random()
        _activeShape.offset = Position(5, 0)
        _board.setCells(
            _activeShape.getPositions(), Cell(CellType.Square,  _activeShape.color)
        )
    }

    fun resetGame() {
        _board = BoardState(rows, columns)
        _speed = 1000
        _score = 0
        _gameOver = false
        _tickTimer?.cancel()
        _uiState.value = GameUiState(
            score = _score,
            gameOver = _gameOver,
            boardCells = _board.cells,
            comparable = ++ _comparable
            )

        getNewShape()
        tick()
    }

   private fun tick() {
       var removeRowsDelay: Int = 0
       if (!_preventTick &&
           !moveShape(0, 1)) {
            val fullRows = _board.fullRows()
            if (fullRows.size > 0) {
                _score += 10 * fullRows.size
                _uiState.update { currentState ->
                    currentState.copy(score = _score,
                                    rowsToRemove = fullRows)
                    }
                removeRowsDelay = 1000
                Timer("resetRowsToRemove", false)
                    .schedule(delay = removeRowsDelay.toLong()){
                        _uiState.update {currentState ->
                            currentState.copy(
                                boardCells = _board.cells,
                                rowsToRemove = mutableListOf<Int>(),
                                comparable = ++ _comparable
                            )
                        }
                    }
                }
            if (_activeShape.offset.y > 0) {
                getNewShape()
            } else {
                _gameOver = true
                _uiState.update { currentState ->
                    currentState.copy(gameOver = _gameOver)
                }
            }
        }
        if (!_gameOver)
            _tickTimer = Timer("nextTick", false)
                .schedule(delay = (_speed + removeRowsDelay).toLong()){
                tick()
            }
    }

    fun moveShape(dx: Int, dy: Int, force: Boolean = false): Boolean {
        if (!force &&
            !_board.canMove(_activeShape.getPositions(), dx, dy)) {
            return false
        }

        val positionsToRemove = _activeShape.getPositions()
        _activeShape.move(dx, dy)
        _board.updateCells(
            positionsToRemove,
            _activeShape.getPositions(),
            Cell(CellType.Square, _activeShape.color)
        )
        _uiState.update { currentState ->
            currentState.copy(boardCells = _board.cells,
                comparable = ++ _comparable)
        }
        return true
    }

    fun rotateShape(){
        _preventTick = true
        val positionsToRemove = _activeShape.getPositions()
        _activeShape.rotate()
        val offset: Offset? = _board.isInBounds(_activeShape.getPositions())
        if(offset != null){
            moveShape(offset.x.toInt(), offset.y.toInt(), true)
        }
        _board.updateCells(positionsToRemove,
                        _activeShape.getPositions(),
                        Cell(CellType.Square, _activeShape.color))

        _uiState.update { currentState ->
            currentState.copy(boardCells = _board.cells,
                comparable = ++ _comparable)
        }
        _preventTick = false
    }

    fun moveIfInShape(position: Position, offset: Offset){
        if(_activeShape.getPositions().contains(position)){
            moveShape(offset.x.toInt(), offset.y.toInt())
        }
    }

    fun rotateIfInShape(position: Position){
        if(_activeShape.getPositions().contains(position)){
            rotateShape()
        }
    }
}