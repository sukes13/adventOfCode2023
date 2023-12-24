package be.sukes13.aoc23.day5

import be.sukes13.aoc23.day7.Card.*
import be.sukes13.aoc23.day7.Hand.*
import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass


class SeedLocationsTest {
    @Test
    fun `parse first line of input, correct hand and bid returned`() {
        val input = readFile("aoc23/day5/exampleInput.txt")
        val seeds = input.toSeeds()
        val destinationMappers = input.toDestinationMappers()

        destinationMappers.first().getNewRangesFor(seeds.first())
        
    }

}
