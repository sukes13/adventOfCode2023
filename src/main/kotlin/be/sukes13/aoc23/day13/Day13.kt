package be.sukes13.aoc23.day13

import be.sukes13.Grid
import be.sukes13.aoc23.day13.Item.ASH
import be.sukes13.aoc23.day13.Item.ROCK
import be.sukes13.column
import be.sukes13.splitOnEmptyLine

fun part1(input: String) = input.splitOnEmptyLine().toValleys().sumOf { it.mirrorAtWithSmudges()!! }

fun part2(input: String): Int = input.splitOnEmptyLine().toValleys().sumOf { it.mirrorAtWithSmudges(1)!! }

private fun Map<Int, List<Item>>.mirrorAtWithSmudges(maxSmudges: Int = 0): Int? {
    val rowMirror = consecutiveEqualRowsAt()
        .singleOrNull { isRowMirror(it, maxSmudges) }
        ?.plus(1)?.times(100)
    val columnMirror = consecutiveEqualColumnsAt()
        .singleOrNull { isColumnMirror(it, maxSmudges) }
        ?.plus(1)

    return rowMirror ?: columnMirror
}

private fun Map<Int, List<Item>>.isColumnMirror(equalColumn: Int, maxSmudges: Int = 0) =
    mirroredPairsAround(equalColumn)
        .filter { column(it.first).first() != null && column(it.second).first() != null }
        .sumOf {
            column(it.first).countSmudges(column(it.second))
        } == maxSmudges

private fun Map<Int, List<Item>>.isRowMirror(equalRow: Int, maxSmudges: Int = 0) =
    mirroredPairsAround(equalRow)
        .filter { this[it.first] != null && this[it.second] != null }
        .sumOf {
            this[it.first]!!.countSmudges(this[it.second]!!)
        } == maxSmudges

private fun Grid<Item>.mirroredPairsAround(mirrorAt: Int) =
    (0 until size).map { mirrorAt - it to mirrorAt + it + 1 }

private fun Grid<Item>.consecutiveEqualRowsAt(): List<Int> =
    entries.zipWithNext()
        .filter { (a, b) -> a.value.equalWithOrWithoutSmudge(b.value) }
        .map { it.first.key }

private fun Grid<Item>.consecutiveEqualColumnsAt(): List<Int> =
    (0 until values.first().size)
        .map { it to column(it) }
        .zipWithNext()
        .filter { (a, b) -> a.second.equalWithOrWithoutSmudge(b.second) }
        .map { it.first.first }

private fun List<Item?>.equalWithOrWithoutSmudge(other: List<Item?>) =
    zip(other).count { (a, b) -> a != b }.let { it == 0 || it == 1 }

private fun List<Item?>.countSmudges(other: List<Item?>) = zip(other).count { (a, b) -> a != b }

private fun List<String>.toValleys() =
    map { valleyString ->
        valleyString.lines().mapIndexed { index, line ->
            index to line.toList().map { if (it == '#') ROCK else ASH }
        }.toMap()
    }

enum class Item { ROCK, ASH }
