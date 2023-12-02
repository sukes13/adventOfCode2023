package be.sukes13.aoc23.day2

import be.sukes13.mapLines
import be.sukes13.readFile
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test

class CubeConundrumTest {
    @Test
    fun `parse string to CubeGame`() {
        val input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        val expected = CubeGame(
            id = 1,
            biggestReveals = mapOf(
                CubeColor.BLUE to 6,
                CubeColor.RED to 4,
                CubeColor.GREEN to 2,
            )
        )

        assertThat(input.toCubeGame()).isEqualTo(expected)
    }

    @Test
    fun `parse string game 3 to CubeGame`() {
        val input = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"
        val expected = CubeGame(
            id = 3,
            biggestReveals = mapOf(
                CubeColor.BLUE to 6,
                CubeColor.RED to 20,
                CubeColor.GREEN to 13,
            )
        )

        assertThat(input.toCubeGame()).isEqualTo(expected)
    }
}