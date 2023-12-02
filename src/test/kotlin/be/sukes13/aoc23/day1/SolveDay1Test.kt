package be.sukes13.aoc23.day1

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay1Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("aoc23/day1/exampleInput.txt")
        assertThat(part1(input)).isEqualTo(142)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("aoc23/day1/input.txt")
        assertThat(part1(input)).isEqualTo(54877)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("aoc23/day1/exampleInput2.txt")
        assertThat(part2(input)).isEqualTo(281)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("aoc23/day1/input2.txt")
        assertThat(part2(input)).isEqualTo(54100)
    }
}