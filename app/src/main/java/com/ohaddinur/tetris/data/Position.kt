package com.ohaddinur.tetris.data

data class Position(val x: Int, val y: Int) : Comparable<Position> {
    override fun compareTo(other: Position): Int {
        // Compare the x-coordinates first
        val xComparison = x.compareTo(other.x)
        if (xComparison != 0) {
            return xComparison
        }

        // If the x-coordinates are equal, compare the y-coordinates
        return y.compareTo(other.y)
    }
    
    fun isNear(other: Position): Boolean {
        return (x == other.x && y == other.y + 1) ||
                (x == other.x && y == other.y - 1) ||
                (x == other.x + 1 && y == other.y) ||
                (x == other.x - 1 && y == other.y)
    }
}