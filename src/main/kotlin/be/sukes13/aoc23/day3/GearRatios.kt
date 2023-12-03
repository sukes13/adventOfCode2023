package be.sukes13.aoc23.day3

import be.sukes13.Direction
import be.sukes13.Point
import be.sukes13.stepInDirection

fun part1(input: String): Int {
    val allOccupiedSpots = input.toSpots()
    val numbersInGrid = allOccupiedSpots.toNumbersInGrid()

    return allOccupiedSpots.numbersThatTouchSymbol(numbersInGrid).sumOf { it.value }
}

fun part2(input: String): Int {
    val allOccupiedSpots = input.toSpots()
    val numbersInGrid = allOccupiedSpots.toNumbersInGrid()

    return allOccupiedSpots.gearRatios(numbersInGrid).sum()
}


internal fun String.toSpots() = lines().flatMapIndexed { y, line ->
    line.mapIndexed { x, char ->
        if (char != '.') GridSpot(Point(x, y), char)
        else null
    }
}.filterNotNull()

internal fun List<GridSpot>.toNumbersInGrid() =
    mapNotNull { spot ->
        if (spot.character.isDigit()) {
            gridSpotsOnRowOf(spot).numberStartingFrom(spot).let { number ->
                if (isMissingDigitToTheLeft(spot)) null
                else NumberInGrid(
                    value = number.map { it.character }.joinToString("").toInt(),
                    points = number.map { it.point }
                )
            }
        } else null
    }

private fun List<GridSpot>.numberStartingFrom(spot: GridSpot, number: List<GridSpot> = listOf(spot)): List<GridSpot> =
    neighbourSpotInDirection(spot, Direction.RIGHT)
        ?.let { rightNeighbourSpot ->
            if (rightNeighbourSpot.character.isDigit()) {
                numberStartingFrom(rightNeighbourSpot, number + rightNeighbourSpot)
            } else number
        } ?: number

private fun List<GridSpot>.numbersThatTouchSymbol(numbersInGrid: List<NumberInGrid>) =
    mapNotNull { (point, character) ->
        if (!character.isDigit()) neighbouringNumbersInGrid(point, numbersInGrid)
        else null
    }.flatten().distinct()

private fun List<GridSpot>.gearRatios(numbersInGrid: List<NumberInGrid>) =
    mapNotNull { (point, character) ->
        if (character == '*') {
            neighbouringNumbersInGrid(point, numbersInGrid)
                .distinct()
                .calculateGearRation()
        } else null
    }

private fun List<NumberInGrid>.calculateGearRation() =
    if (size == 2) map { it.value }.reduce { total, i -> total * i }
    else null

private fun List<GridSpot>.gridSpotsOnRowOf(spot: GridSpot) =
    filter { it.point.y == spot.point.y }

private fun List<GridSpot>.isMissingDigitToTheLeft(spot: GridSpot) =
    neighbourSpotInDirection(spot, Direction.LEFT)?.character?.isDigit() == true

private fun List<GridSpot>.neighbourSpotInDirection(spot: GridSpot, direction: Direction) =
    singleOrNull { it.point == (spot.point.stepInDirection(direction)) }

private fun neighbouringNumbersInGrid(point: Point, numbersInGrid: List<NumberInGrid>) =
    point.neighbours.flatMap { neighbour -> numbersInGrid.filter { neighbour in it.points } }

data class GridSpot(val point: Point, val character: Char)
data class NumberInGrid(val value: Int, val points: List<Point>)

