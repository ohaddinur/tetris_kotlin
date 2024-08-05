package com.ohaddinur.tetris.data

import androidx.compose.ui.geometry.Offset

data class BoardState(
    private val _rows: Int,
    private val _columns: Int,
) {
    private var _cells: MutableList<MutableList<Cell>> = MutableList(_rows) { MutableList(_columns) { Cell() } }

    val cells : List<List<Cell>>
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

    fun updateCells(positionsToRemove: List<Position>,
                    positionsToAdd: List<Position>,
                    cell: Cell) {
        setCells(positionsToRemove, Cell())
        setCells(positionsToAdd, cell)
    }

    fun canMove(positions: List<Position>, dx: Int, dy: Int): Boolean {
        //Check if shape can move to a given offset
        for (position in positions) {
            val newPosition = Position(position.x + dx, position.y + dy)
            //check if new position is not in current positions (a part of the shape)
            if (positions.contains(newPosition)) {
                continue
            }
            //Check if position is within board's bounds
            if (newPosition.x < 0 || newPosition.x >= _columns ||
                newPosition.y < 0 || newPosition.y >= _rows) {
                return false
            }
            //Check if position is not empty
            if (_cells[newPosition.y][newPosition.x].type != CellType.None) {
                return false
            }
        }
        return true
    }

    fun isInBounds(positions: List<Position>): Offset? {
        //Check if shape's position are in board bounds,
        //if not return offset to correct
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
        //Check for full rows and remove them
        //Returns a list of the full rows indexes
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