package be.sukes13.aoc23.day13

import be.sukes13.Grid
import be.sukes13.aoc23.day13.Item.ASH
import be.sukes13.aoc23.day13.Item.ROCK
import be.sukes13.column
import be.sukes13.splitOnEmptyLine

fun part1(input: String) = input.splitOnEmptyLine().toValleys().sumOf { it.mirrorAt()!! }

fun part2(input: String): Int = 0

private fun Map<Int, List<Item>>.mirrorAt(): Int? {
    val rowMirror = neighbouringEqualRowsAt()
        .singleOrNull { isRowMirror(it) }
        ?.plus(1)?.times(100)

    val columnMirror = neighbouringEqualColumnsAt()
        .singleOrNull { isColumnMirror(it) }
        ?.plus(1)

    return rowMirror ?: columnMirror
}

private fun Map<Int, List<Item>>.isColumnMirror(equalColumn: Int) =
    mirroredAround(equalColumn).filterNot {
        val columnOne = column(it.first)
        val columnTwo = column(it.second)
        columnOne.first() == null || columnTwo.first() == null || columnOne == columnTwo
    }.isEmpty()

private fun Map<Int, List<Item>>.isRowMirror(equalRow: Int) =
    mirroredAround(equalRow).filterNot {
        val rowOne = this[it.first]
        val rowTwo = this[it.second]
        rowOne == null || rowTwo == null || rowOne == rowTwo
    }.isEmpty()

private fun Grid<Item>.mirroredAround(mirrorAt: Int) =
    (0 until size).map { mirrorAt - it to mirrorAt + it + 1 }

private fun Grid<Item>.neighbouringEqualRowsAt(): List<Int> =
    entries.zipWithNext()
        .filter { (a, b) -> a.value == b.value }
        .map { it.first.key }

private fun Grid<Item>.neighbouringEqualColumnsAt(): List<Int> =
    (0 until values.first().size)
        .map { it to column(it) }
        .zipWithNext()
        .filter { (a, b) -> a.second == b.second }
        .map { it.first.first }

private fun List<String>.toValleys() =
    map {
        it.lines().mapIndexed { index, line ->
            index to line.toList().map { if (it == '#') ROCK else ASH }
        }.toMap()
    }

enum class Item { ROCK, ASH }
