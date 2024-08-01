package com.ohaddinur.tetris.data
import kotlin.math.roundToInt
import kotlin.random.Random
import androidx.compose.ui.graphics.Color


class Shape(private var shape: List<List<Boolean>>, offset: Position? = null) {
    var offset: Position = offset ?: Position(0, 0)
    var color: Color = getRandomColor()

    private val width: Int = shape[0].size

    private val height: Int = shape.size

    fun getPositions(): List<Position> {
        val positions = mutableListOf<Position>()
        val backToCenterX = (width / 2.0).roundToInt()
        val backToCenterY = (height / 2.0).roundToInt()
        for (y in shape.indices) {
            for (x in shape[y].indices) {
                if (shape[y][x]) {
                    positions.add(Position(
                        x + offset.x - backToCenterX, y + offset.y - backToCenterY
                    ))
                }
            }
        }
        return positions
    }

    fun move(dx: Int, dy: Int) {
        offset = Position(offset.x + dx, offset.y + dy)
    }

    fun rotate() {
        val newShape = List(shape[0].size) { List(shape.size) { false }.toMutableList() }
        for (y in shape.indices) {
            for (x in shape[y].indices) {
                newShape[x][shape.size - 1 - y] = shape[y][x]
            }
        }
        shape = newShape
    }

    companion object {
        fun random(): Shape {
            val shapes = listOf(
                listOf(
                    listOf(true, true, true, true)
                ),
                listOf(
                    listOf(true, true),
                    listOf(true, true)
                ),
                listOf(
                    listOf(true, true, true),
                    listOf(false, false, true)
                ),
                listOf(
                    listOf(true, true, true),
                    listOf(true, false, false)
                ),
                listOf(
                    listOf(true, true, false),
                    listOf(false, true, true)
                ),
                listOf(
                    listOf(false, true, true),
                    listOf(true, true, false)
                ),
                listOf(
                    listOf(true, true, true),
                    listOf(false, true, false)
                )
            )
            return Shape(shapes[Random.nextInt(shapes.size)])
        }
    }

    private fun getRandomColor(): Color {
        val colors = listOf(
            Color.Green,
            Color.Blue,
            Color.Yellow,
            Color.Magenta,
            Color(0xFFFFA500), // Orange
            Color(0xFFFFC0CB)  // Pink
        )
        return colors[Random.nextInt(colors.size)]
    }
}