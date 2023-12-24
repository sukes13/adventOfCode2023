package be.sukes13.aoc23.day5

import be.sukes13.splitOnEmptyLine

fun part1(input: String) = input.toDestinationMappers().let { destinationMappers ->
    input.toSeeds().minOfOrNull { destinationMappers.toLocationNumber(it) }
}

fun part2(input: String) = input.toDestinationMappers().let { destinationMappers ->
    val seedPairs = listOf(input.toSeeds().windowed(2, 2).map { (a, b) -> a until a + b }.first())
    destinationMappers.fold(seedPairs) { acc, destinationMapper ->
        println("New ranges: $acc")
        destinationMapper.getNewRangesFor(acc)
    }
}.minBy { it.first }.first

fun List<DestinationMapper>.getNewRangesFor(rangesIn: List<LongRange>): List<LongRange> {
    return flatMap { destinationMapper ->
        rangesIn.mapNotNull {
            var startOfRange = it.first
            var endOfRange = it.last
            if (endOfRange < destinationMapper.seedStart || startOfRange > destinationMapper.seedEnd) {
                return@mapNotNull null
            }
            if (endOfRange >= destinationMapper.seedStart) {
                endOfRange = destinationMapper.mapToDestination(endOfRange)
            }
            if (startOfRange <= destinationMapper.seedEnd) {
                startOfRange = destinationMapper.mapToDestination(startOfRange)
            }
            startOfRange..endOfRange
        }
    }.let {
        it.ifEmpty { rangesIn }
    }

}


private fun List<List<DestinationMapper>>.toLocationNumber(seed: Long) =
    fold(seed) { acc, roundOfMappers ->
        roundOfMappers.findMapper(acc)?.mapToDestination(acc) ?: acc
    }

private fun List<DestinationMapper>.findMapper(seed: Long) = singleOrNull { seed in it.seedStart until it.seedEnd }


data class DestinationMapper(val seedStart: Long, val destinationStart: Long, val length: Long) {
    val seedEnd = seedStart + length
    fun mapToDestination(input: Long) = destinationStart + (input - seedStart)
}

internal fun String.toDestinationMappers() = splitOnEmptyLine().drop(1).map { mapperBlock ->
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

internal fun String.toSeeds() = lines().first().split(" ").drop(1).map(String::toLong)
