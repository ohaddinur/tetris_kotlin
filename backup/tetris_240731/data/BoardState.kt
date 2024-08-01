package com.ohaddinur.tetris.data

import androidx.compose.ui.graphics.Color

data class Cell(val type: CellType = CellType.None, val color: Color = Color.Transparent)

enum class CellType {
    None, Square
}

data class BoardState(
    private val rows: Int,
    private val columns: Int,
) {
    private val _cells: MutableList<MutableList<Cell>> = MutableList(rows) { MutableList(columns) { Cell() } }

    val cells : MutableList<MutableList<Cell>>
            get() = _cells

    private fun setAt(x: Int, y: Int, cell: Cell) {
        if (x < 0 || x >= columns || y < 0 || y >= rows) return
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
            if (newPosition.x < 0 || newPosition.x >= columns || newPosition.y < 0 || newPosition.y >= rows) {
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

    fun rowFull(): Int {
        var fullRows = 0
        for (y in rows - 1 downTo 0) {
            if (_cells[y].all { cell -> cell.type != CellType.None }) {
                _cells.removeAt(y)
                _cells.add(0, MutableList(columns) { Cell() })
                fullRows++
            }
        }
        return fullRows
    }
}