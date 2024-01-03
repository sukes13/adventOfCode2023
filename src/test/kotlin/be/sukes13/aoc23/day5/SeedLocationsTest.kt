package be.sukes13.aoc23.day5

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class SeedLocationsTest {
    @Test
    fun `parse first line of input, correct hand and bid returned`() {
        val input = readFile("aoc23/day5/exampleInput.txt")
        val seedRange = 79L .. 92
        val rangeMappers = input.toRangeMappers()

        assertThat(rangeMappers.first().transformRange(seedRange)).contains(81..94L)
//
//            81..94
//        81..94
//        74..87
//        55.78
//       56..78

    }

}
