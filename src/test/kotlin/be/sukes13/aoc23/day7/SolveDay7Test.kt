package be.sukes13.aoc23.day7

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay7Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("aoc23/day7/exampleInput.txt")
        assertThat(part1(input)).isEqualTo(6440)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("aoc23/day7/input.txt")
        assertThat(part1(input)).isEqualTo(251121738)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("aoc23/day7/exampleInput.txt")
        assertThat(part2(input)).isEqualTo(5905)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("aoc23/day7/input.txt")
        assertThat(part2(input)).isEqualTo(251421071)
    }
}