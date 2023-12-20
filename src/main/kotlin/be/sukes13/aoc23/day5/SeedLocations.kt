package be.sukes13.aoc23.day5

import be.sukes13.splitOnEmptyLine

fun part1(input: String) = input.toDestinationMappers().let { destinationMappers ->
    input.toSeeds().minOfOrNull { destinationMappers.toLocationNumber(it) }
}

fun part2(input: String) = 0

private fun List<List<DestinationMapper>>.toLocationNumber(seed: Long) =
    fold(seed) { acc, roundOfMappers ->
        roundOfMappers.findMapper(acc)?.mapToDestination(acc) ?: acc
    }

private fun List<DestinationMapper>.findMapper(seed: Long) =
    singleOrNull { seed in it.seedStart until (it.seedStart + it.length) }

data class DestinationMapper(val seedStart: Long, val destinationStart: Long, val length: Long) {
    fun mapToDestination(input: Long) = destinationStart + (input - seedStart)
}

private fun String.toDestinationMappers() = splitOnEmptyLine().drop(1).map { mapperBlock ->
    mapperBlock.lines().drop(1).map { line ->
        line.split(" ").let { splitLine ->
            DestinationMapper(
                seedStart = splitLine[1].toLong(),
                destinationStart = splitLine.first().toLong(),
                length = splitLine.last().toLong()
            )
        }
    }
}

private fun String.toSeeds() = lines().first().split(" ").drop(1).map(String::toLong)
