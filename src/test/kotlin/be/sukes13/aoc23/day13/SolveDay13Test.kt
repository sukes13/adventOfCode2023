package be.sukes13.aoc23.day13

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay13Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("aoc23/day13/exampleInput.txt")
        assertThat(part1(input)).isEqualTo(405)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("aoc23/day13/input.txt")
        assertThat(part1(input)).isEqualTo(36015)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("aoc23/day13/exampleInput.txt")
        assertThat(part2(input)).isEqualTo(400)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("aoc23/day13/input.txt")
        assertThat(part2(input)).isEqualTo(0)
    }

}