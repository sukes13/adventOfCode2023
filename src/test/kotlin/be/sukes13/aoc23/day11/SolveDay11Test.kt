package be.sukes13.aoc23.day11

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class SolveDay11Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("aoc23/day11/exampleInput.txt")
        assertThat(part1(input)).isEqualTo(374L)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("aoc23/day11/input.txt")
        assertThat(part1(input)).isEqualTo(10165598L)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("aoc23/day11/exampleInput.txt")
        assertThat(part2(input,100)).isEqualTo(8410L)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("aoc23/day11/input.txt")
        assertThat(part2(input,1_000_000)).isEqualTo(678728808158L)
    }
}