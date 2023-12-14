package be.sukes13.aoc23.day14

import be.sukes13.Point
import be.sukes13.aoc23.day14.Direction.*

fun part1(input: String) = input.toPlatform().tiltedTo(NORTH).load()

fun part2(input: String) = input.toPlatform().cyclingWithPattern(1_000_000_000)


data class Platform(val balls: List<Point>, val rocks: List<Point>) {
    private val eastEdge = rocks.maxBy { it.x }.x
    private val southEdge = rocks.maxBy { it.y }.y

    fun cyclingWithPattern(cycles: Int) =
        detectPattern(cycles)?.let { pattern ->
            val cyclesWithPattern = cycles - pattern.patternStartCycle
            val patternRepeats = cyclesWithPattern / pattern.patternLength
            val remainingCycles = cyclesWithPattern - (patternRepeats * pattern.patternLength)

            pattern.pattern[remainingCycles - 1]
        }

    private fun detectPattern(cycles: Int, maxPatternSize: Int = 10): PlatformLoadPattern? {
        val allLoads = mutableListOf<Int>()
        val cyclesMap = mutableMapOf<List<Int>, Int>()
        var platform = this

        repeat(cycles) { cycle ->
            platform = platform.oneCycle().also { newPlatform ->
                allLoads.add(newPlatform.load()).also {
                    cyclesMap.put(allLoads.takeLast(maxPatternSize), cycle + 1)
                        ?.let { patternDetectedAt ->
                            val patternLength = allLoads.size - patternDetectedAt
                            val pattern = cyclesMap.keys.toList()[patternDetectedAt - 1].take(patternLength)
                            val patternStartCycle = patternDetectedAt - (maxPatternSize - patternLength)
                            println("PATTERN DETECTED from cycle: $patternStartCycle - $pattern")
                            return PlatformLoadPattern(patternStartCycle, patternLength, pattern)
                        }
                }
            }
        }
        return null
    }

    fun oneCycle() = tiltedTo(NORTH).tiltedTo(WEST).tiltedTo(SOUTH).tiltedTo(EAST)

    tailrec fun tiltedTo(direction: Direction = NORTH, balls: List<Point> = this.balls): Platform {
        val movedBalls = oneTickTiltedTo(direction, balls)
        return if (balls.toHashSet() == movedBalls.toHashSet()) copy(balls = movedBalls)
        else tiltedTo(direction, movedBalls)
    }

    private fun oneTickTiltedTo(direction: Direction, balls: List<Point>) =
        balls.map {
            when (direction) {
                NORTH -> balls.rollNorth(it)
                WEST -> balls.rollWest(it)
                SOUTH -> balls.rollSouth(it)
                EAST -> balls.rollEast(it)
            }
        }

    private fun List<Point>.rollNorth(ball: Point) =
        (ball + Point(0, -1)).let { movedBall ->
            if (canMoveOneStep(ball.y > 0, movedBall)) movedBall else ball
        }

    private fun List<Point>.rollWest(ball: Point) =
        (ball + Point(-1, 0)).let { movedBall ->
            if (canMoveOneStep(ball.x > 0, movedBall)) movedBall else ball
        }

    private fun List<Point>.rollSouth(ball: Point) =
        (ball + Point(0, 1)).let { movedBall ->
            if (canMoveOneStep(ball.y < southEdge, movedBall)) movedBall else ball
        }

    private fun List<Point>.rollEast(ball: Point) =
        (ball + Point(1, 0)).let { movedBall ->
            if (canMoveOneStep(ball.x < eastEdge, movedBall)) movedBall else ball
        }

    private fun List<Point>.canMoveOneStep(onPlatform: Boolean, movedBall: Point) =
        onPlatform && movedBall !in rocks && movedBall !in this

    fun load(): Int {
        val max = rocks.maxBy { it.y }.y + 1
        return balls.sumOf { max - it.y }
    }

    data class PlatformLoadPattern(
        val patternStartCycle: Int,
        val patternLength: Int,
        val pattern: List<Int>
    )
}

fun String.toPlatform() = Platform(balls = toPoints('O'), rocks = toPoints('#'))

internal fun String.toPoints(symbol: Char) = lines().flatMapIndexed { y, line ->
    line.mapIndexed { x, spot ->
        if (spot == symbol) Point(x, y) else null
    }.filterNotNull()
}

enum class Direction { NORTH, EAST, SOUTH, WEST; }

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
