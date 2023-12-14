package be.sukes13.aoc23.day14

import be.sukes13.Point
import be.sukes13.aoc23.day14.Direction.*
import be.sukes13.aoc23.day14.Points.toTheNorth

fun part1(input: String) = input.toPlatform().tiltTo(NORTH).load()

fun part2(input: String) = 0

fun String.toPlatform() =
    Platform(
        balls = toRocks('O'),
        rocks = toRocks('#'),
    )

fun String.toRocks(symbol: Char) = lines().flatMapIndexed { y, line ->
    line.mapIndexed { x, spot ->
        if (spot == symbol) Point(x, y) else null
    }.filterNotNull().toHashSet()
}

internal fun Platform.visualize(): String {
    val maxX = rocks.maxBy { it.x }.x
    val maxY = rocks.maxBy { it.y }.y
    return (0..maxY).joinToString("\n") { y ->
        (0..maxX).joinToString("") { x ->
            if (Point(x, y) in balls) "O"
            else if (Point(x, y) in rocks) "#"
            else "."
        }
    }
}

data class Platform(val balls: List<Point>, val rocks: List<Point>) {
    fun tiltTo(direction: Direction = NORTH, balls: List<Point> = this.balls): Platform {
        val movedBalls = oneTickTiltedTo(direction,balls)
        return if(balls == movedBalls) copy(balls = movedBalls)
        else tiltTo(direction,movedBalls)
    }

    private fun oneTickTiltedTo(north: Direction,balls: List<Point>) =
        balls.sortedBy { it.y }.map { ball ->
            if (ball.y > 0 && (rocks+balls).none { it == ball.toTheNorth() })
                ball.copy(y = ball.y - 1)
            else ball
        }

    fun load(): Int {
        val max = rocks.maxBy { it.y }.y + 1
        return balls.sumOf { max - (it.y) }
    }
}

object Points {
    fun Point.toTheNorth(): Point = this + Point(0, -1)
    fun Point.pointLeft(): Point = this + Point(-1, 1)
    fun Point.pointRight(): Point = this + Point(1, 1)
}

enum class Direction { NORTH, EAST, SOUTH, WEST; }