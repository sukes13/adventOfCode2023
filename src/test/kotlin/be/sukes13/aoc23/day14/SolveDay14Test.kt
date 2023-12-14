package be.sukes13.aoc23.day14

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay14Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("aoc23/day14/exampleInput.txt")
        assertThat(part1(input)).isEqualTo(136)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("aoc23/day14/input.txt")
        assertThat(part1(input)).isEqualTo(105461)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("aoc23/day14/exampleInput.txt")
        assertThat(part2(input)).isEqualTo(64)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("aoc23/day14/input.txt")
        assertThat(part2(input)).isEqualTo(102829)
    }
}