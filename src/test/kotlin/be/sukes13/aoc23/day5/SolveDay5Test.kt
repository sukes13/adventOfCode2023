package be.sukes13.aoc23.day5

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay5Test {

    @Test
    fun `example input part 1`() {
        val input = readFile("aoc23/day5/exampleInput.txt")
        assertThat(part1(input)).isEqualTo(35)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("aoc23/day5/input.txt")
        assertThat(part1(input)).isEqualTo(825516882)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("aoc23/day5/exampleInput.txt")
        assertThat(part2(input)).isEqualTo(46)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("aoc23/day5/input.txt")
        assertThat(part2(input)).isEqualTo(251421071)
    }
}