package be.sukes13.aoc23.day2

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SolveDay2Test {
    private val toMatchPart1 = mapOf(CubeColor.RED to 12, CubeColor.GREEN to 13, CubeColor.BLUE to 14)

    @Test
    fun `example input part 1`() {
        val input = readFile("aoc23/day2/exampleInput.txt")
        assertThat(part1(input, toMatchPart1)).isEqualTo(8)
    }

    @Test
    fun `actual input part 1`() {
        val input = readFile("aoc23/day2/input.txt")
        assertThat(part1(input, toMatchPart1)).isEqualTo(1853)
    }

    @Test
    fun `example input part 2`() {
        val input = readFile("aoc23/day2/exampleInput.txt")
        assertThat(part2(input)).isEqualTo(2286)
    }

    @Test
    fun `actual input part 2`() {
        val input = readFile("aoc23/day2/input.txt")
        assertThat(part2(input)).isEqualTo(72706)
    }
}