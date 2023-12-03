package be.sukes13.aoc23.day3

import be.sukes13.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


class GearRatiosTest {
    @ParameterizedTest
    @MethodSource("numbersInGrid")
    fun `parse input`(input: String, result: List<NumberInGrid>) {
        assertThat(input.toSpots().toNumbersInGrid()).containsExactlyInAnyOrder(*result.toTypedArray())
    }

    companion object {
        @JvmStatic
        fun numbersInGrid(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "467..114..", listOf(
                    NumberInGrid(467, listOf(Point(0, 0), Point(1, 0), Point(2, 0))),
                    NumberInGrid(114, listOf(Point(5, 0), Point(6, 0), Point(7, 0))),
                )
            ),
            Arguments.of(
                "...*.12.9.", listOf(
                    NumberInGrid(12, listOf(Point(5, 0), Point(6, 0))),
                    NumberInGrid(9, listOf(Point(8, 0))),
                )
            ),
        )
    }

}

