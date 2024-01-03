package be.sukes13.aoc23.day5

import be.sukes13.splitOnEmptyLine

fun part1(input: String) = input.toRangeMappers().let { rangeMappers ->
    input.toSeeds().minOfOrNull { seed ->
        rangeMappers.fold(seed) { acc, rangeMapper -> rangeMapper.transform(acc) }
    }
}


fun part3(input: String) = input.toRangeMappers().let { rangeMappers ->
    input.toSeedRanges().first().map { seed ->
        println("new seed: $seed ")
        rangeMappers.fold(seed) { acc, rangeMapper ->
            rangeMapper.transform(acc).also { println(acc) }
        }
    }
}

fun part2(input: String) = input.toRangeMappers().let { rangeMappers ->
//    input.toSeedRanges().map { seedRange ->
//        rangeMappers.fold(seedRange) { acc, rangeMapper -> rangeMapper.transformRange(acc) }
//    }.min()
}


internal fun String.toRangeMappers() = splitOnEmptyLine().drop(1).map { mapperBlock ->
    mapperBlock.lines().drop(1).map { line ->
        line.split(" ").let { splitLine ->
            DestinationMapper(
                seedStart = splitLine[1].toLong(),
                destinationStart = splitLine.first().toLong(),
                length = splitLine.last().toLong()
            )
        }
    }
}.map { it.combineMappers() }


internal fun String.toSeeds() = lines().first().split(" ").drop(1).map(String::toLong)

internal fun String.toSeedRanges() = toSeeds().windowed(2, 2).map { (a, b) -> a until a + b }

private fun List<DestinationMapper>.combineMappers() =
    RangeMapper(
        associate { it.seedStart until it.seedEnd to { a: Long -> it.mapToDestination(a) } }
    )

private fun Set<LongRange>.sort() = sortedBy { it.first }

class RangeMapper(private val rangeParts: Map<LongRange, (Long) -> Long>) {
    private val sortedRangeParts = rangeParts.toSortedMap(compareBy { it.first })

    fun transform(input: Long) =
        findRangePart(input)
            ?.let { it.value(input) }
            ?: input

    private fun findRangePart(input: Long) = rangeParts.entries.singleOrNull { it.key.contains(input) }

    fun transformRange(inRanges: List<LongRange>): List<LongRange> {
        if (inRanges.notOverlapping(sortedRangeParts.keys)) return inRanges
        else {
            inRanges.map { inRange ->
                var start = inRange.first
                sortedRangeParts.map { rangePart ->
                    if(inRange.first in rangePart.key)
                        start = rangePart.value(inRange.first)
                    if(inRange.last)


                }

            }
        }
        sortedRangeParts.first().first..sortedRangeParts.last().last

    }

}

private fun LongRange.notOverlapping(longRanges: LongRange) =
    longRanges.last < first || longRanges.first > last

private fun List<LongRange>.notOverlapping(longRanges: Set<LongRange>) =
    longRanges.last().last < first().first || longRanges.first().first > last().last

data class DestinationMapper(val seedStart: Long, val destinationStart: Long, val length: Long) {
    val seedEnd = seedStart + length
    fun mapToDestination(input: Long): Long = destinationStart + (input - seedStart)
}
