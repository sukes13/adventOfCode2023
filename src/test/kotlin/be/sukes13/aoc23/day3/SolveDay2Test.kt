package be.sukes13.aoc23.day3

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay3Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("aoc23/day3/exampleInput.txt")
        assertThat(part1(input)).isEqualTo("")
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("aoc23/day3/input.txt")
        assertThat(part1(input)).isEqualTo("")
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("aoc23/day3/exampleInput.txt")
        assertThat(part2(input)).isEqualTo("")
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("aoc23/day3/input.txt")
        assertThat(part2(input)).isEqualTo("")
    }
}