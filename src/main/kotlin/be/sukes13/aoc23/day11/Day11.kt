package be.sukes13.aoc23.day11

import kotlin.math.absoluteValue

fun part1(input: String) = input.toUniverse().expand().galaxyPairs().shortestPaths().sum()

fun part2(input: String, rate : Int) = input.toUniverse().expand(rate).galaxyPairs().shortestPaths().sum()


fun String.toUniverse() = lines().flatMapIndexed { y, line ->
    line.mapIndexed { x, spot ->
        if (spot == '#') Point(x.toLong(), y.toLong()) else null
    }.filterNotNull()
}

fun Universe.expand(rate: Int = 2) = expandHorizontally(emptyColumns(), rate).expandVertically(emptyRows(), rate)

private fun Universe.expandHorizontally(emptyColumns: List<Long>, rate: Int) = map { galaxy ->
    galaxy.emptyColumnsToTeLeft(emptyColumns).let {
        galaxy.copy(x = galaxy.x + it * (rate -1))
    }
}

private fun Universe.expandVertically(emptyRows: List<Long>, rate: Int) = map { galaxy ->
    galaxy.emptyRowsAbove(emptyRows).let {
        galaxy.copy(y = galaxy.y + it * (rate -1))
    }
}

private fun Point.emptyColumnsToTeLeft(emptyColumns: List<Long>) = emptyColumns.count { it < x }
private fun Point.emptyRowsAbove(emptyRows: List<Long>) = emptyRows.count { it < y }

private fun Universe.emptyColumns(): List<Long> {
    val xList = map { it.x }
    val maxX = maxBy { it.x }.x
    return (0..maxX).filterNot { it in xList }
}

private fun Universe.emptyRows(): List<Long> {
    val yList = map { it.y }
    val maxY = maxBy { it.y }.y
    return (0..maxY).filterNot { it in yList }
}

private fun Universe.galaxyPairs() =
    flatMapIndexed { index, galaxy ->
        slice(index + 1 until size).map { galaxy to it }
    }

private fun List<Pair<Point, Point>>.shortestPaths() =
    map { (galaxy1, galaxy2) -> galaxy1.distanceTo(galaxy2) }

private fun Point.distanceTo(other: Point) = (other.x - x).absoluteValue + (other.y - y).absoluteValue

internal fun Universe.visualize(): String {
    val maxX = maxBy { it.x }.x
    val maxY = maxBy { it.y }.y
    return (0..maxY).joinToString("\n") { y ->
        (0..maxX).joinToString("") { x ->
            if (contains(Point(x, y))) "#" else "."
        }
    }
}

typealias Universe = List<Point>

data class Point(val x: Long, val y: Long)

