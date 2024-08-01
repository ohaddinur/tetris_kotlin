package com.ohaddinur.tetris.data

import androidx.compose.ui.graphics.Color

data class Cell(val type: CellType = CellType.None, val color: Color = Color.Transparent)

enum class CellType {
    None, Square
}

data class BoardState(
    private val _rows: Int,
    private val _columns: Int,
) {
    private val _cells: MutableList<MutableList<Cell>> = MutableList(_rows) { MutableList(_columns) { Cell() } }

    val cells : MutableList<MutableList<Cell>>
            get() = _cells

    private fun setAt(x: Int, y: Int, cell: Cell) {
        if (x < 0 || x >= _columns || y < 0 || y >= _rows) return
        _cells[y][x] = cell
    }

    fun setCells(positions: List<Position>, cell: Cell) {
        positions.forEach { position ->
            setAt(position.x, position.y, cell)
        }
    }

    fun updateCells(positionsToRemove: List<Position>, positionsToAdd: List<Position>, cell: Cell) {
        setCells(positionsToRemove, Cell())
        setCells(positionsToAdd, cell)
    }

    fun canMove(positions: List<Position>, dx: Int, dy: Int): Boolean {
        for (position in positions) {
            val newPosition = Position(position.x + dx, position.y + dy)
            if (positions.contains(newPosition)) {
                continue
            }
            if (newPosition.x < 0 || newPosition.x >= _columns || newPosition.y < 0 || newPosition.y >= _rows) {
                return false
            }
            //check if new position is not in current positions
            if(positions.contains(newPosition)){
                continue
            }
            if (_cells[newPosition.y][newPosition.x].type != CellType.None) {
                return false
            }
        }
        return true
    }

    fun fullRows(): List<Int> {
        val fullRows: MutableList<Int> = mutableListOf()
        for (y in _rows - 1 downTo 0) {
            if (_cells[y].all { cell -> cell.type != CellType.None }) {
                fullRows.add(y)
            }
        }
        for (row in fullRows) {
            _cells.removeAt(row)
            _cells.add(0, MutableList(_columns) { Cell() })
        }
        return fullRows
    }
}