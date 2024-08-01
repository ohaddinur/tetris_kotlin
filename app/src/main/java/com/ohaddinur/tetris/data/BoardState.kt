package com.ohaddinur.tetris.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class Cell(val type: CellType = CellType.None, val color: Color = Color.Transparent)

enum class CellType {
    None, Square
}

data class BoardState(
    private val _rows: Int,
    private val _columns: Int,
) {
    private var _cells: MutableList<MutableList<Cell>> = MutableList(_rows) { MutableList(_columns) { Cell() } }

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
            //check if new position is not in current positions
            if (positions.contains(newPosition)) {
                continue
            }
            if (newPosition.x < 0 || newPosition.x >= _columns ||
                newPosition.y < 0 || newPosition.y >= _rows) {
                return false
            }
            if (_cells[newPosition.y][newPosition.x].type != CellType.None) {
                return false
            }
        }
        return true
    }

    fun isInBounds(positions: List<Position>): Offset? {
        var maxOffsetX = 0
        var maxOffsetY = 0
        for (position in positions) {
            var offsetX: Int
            var offsetY: Int
            if (position.x < 0) {
                offsetX = -position.x
                if (offsetX < maxOffsetX) {
                    maxOffsetX = offsetX
                }
            } else if (position.x >= _columns) {
                offsetX = _columns - position.x - 1
                if (offsetX < maxOffsetX) {
                    maxOffsetX = offsetX
                }
            }
            if (position.y < 0) {
                offsetY = -position.y
                if (offsetY < maxOffsetY) {
                    maxOffsetY = offsetY
                }
            } else if (position.y >= _rows) {
                offsetY = _rows - position.y - 1
                if (offsetY < maxOffsetY) {
                    maxOffsetY = offsetY
                }
            }
        }
        if (maxOffsetX != 0 || maxOffsetY != 0) {
            return Offset(maxOffsetX.toFloat(), maxOffsetY.toFloat())
        }
        return null
    }

    fun fullRows(): List<Int> {
        val fullRows: MutableList<Int> = mutableListOf()
        _cells.forEachIndexed { y, row ->
            if (row.all { cell -> cell.type != CellType.None }) {
                fullRows.add(y)
            }
        }
        var newCells: MutableList<MutableList<Cell>> =
                        MutableList(fullRows.size) { MutableList(_columns) { Cell() } }
        _cells.forEachIndexed { y, row ->
            if (! fullRows.contains(y)) {
                newCells.add(row)
            }
        }
        _cells = newCells
        return fullRows
    }
}