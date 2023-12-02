package be.sukes13.aoc23.day2

import be.sukes13.mapLines
import be.sukes13.aoc23.day2.CubeColor.Companion.toCubeColor

fun part1(input: String, toMatch: Map<CubeColor, Int>) =
    input.mapLines { it.toCubeGame() }
        .filter { cubeGame -> cubeGame.isPossible(toMatch) }
        .sumOf { it.id }

fun part2(input: String) =
    input.mapLines { it.toCubeGame() }
        .sumOf { it.power }


fun String.toCubeGame(): CubeGame {
    val gameId = split(":").first().replace("Game ", "").toInt()
    val biggestReveals = split(":").drop(1).first()
        .toCubeReveals()
        .mergeToBiggestReveals()

    return CubeGame(gameId, biggestReveals)
}

private fun String.toCubeReveals() = split(";").map { setOfCubesString ->
    setOfCubesString.trim()
        .split(", ")
        .map { setOfCubes ->
            setOfCubes.trim().split(" ").let {
                it.last().toCubeColor() to it.first().toInt()
            }
        }
}

private fun List<List<CubeReveal>>.mergeToBiggestReveals(): Map<CubeColor, Int> =
    fold(emptyList<CubeReveal>()) { acc, map ->
        acc + map
    }.groupBy({ it.first }, { it.second })
        .mapValues { it.value.max() }
        .toMap()

typealias CubeReveal = Pair<CubeColor, Int>

data class CubeGame(val id: Int, val biggestReveals: Map<CubeColor, Int>) {
    val power = biggestReveals.values.reduce { total, i -> total * i }

    fun isPossible(toMatch: Map<CubeColor, Int>) = biggestReveals.filterNot { it.value <= toMatch[it.key]!! }.isEmpty()
}

enum class CubeColor {
    RED, BLUE, GREEN;

    companion object {
        fun String.toCubeColor() = valueOf(uppercase())
    }
}

