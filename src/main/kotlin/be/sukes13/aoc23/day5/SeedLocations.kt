package be.sukes13.aoc23.day5

import be.sukes13.splitOnEmptyLine
import javax.print.attribute.standard.Destination
import kotlin.math.absoluteValue

fun part1(input: String) = input.toDestinationMappers().toLocationNumbers(input.toSeeds()).min()

fun part2(input: String) = 0

private fun List<List<DestinationMapper>>.toLocationNumbers(seeds: List<Long>): List<Long> {
    return map { roundOfMappers ->
        roundOfMappers.mapSeeds(seeds).also { println(it) }
    }.last()

}

private fun List<DestinationMapper>.mapSeeds(seeds: List<Long>) =
    seeds.map {
        findMapper(it)?.mapToDestination(it) ?: it
    }

private fun List<DestinationMapper>.findMapper(seed: Long) =
    singleOrNull { seed in it.seedStart..it.seedStart + it.length }

private fun String.toDestinationMappers() = splitOnEmptyLine().drop(1).map { mapperBlock ->
    mapperBlock.lines().drop(1).map { line ->
        line.split(" ").let { splitLine ->
            DestinationMapper(
                seedStart = splitLine.first().toLong(),
                destinationStart = splitLine[1].toLong(),
                length = splitLine.last().toLong()
            )
        }
    }
}

private fun String.toSeeds() = lines().first().replace("seeds: ", "").split(" ").map(String::toLong)
data class DestinationMapper(val seedStart: Long, val destinationStart: Long, val length: Long) {
    fun mapToDestination(input: Long): Long {
        return destinationStart + (destinationStart - seedStart).absoluteValue + input
    }
}
