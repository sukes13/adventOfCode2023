package be.sukes13.aoc23.day14

import be.sukes13.aoc23.day11.toUniverse
import be.sukes13.aoc23.day11.visualize
import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day14Test {
    @Test
    fun `parse input`() {
        val input = readFile("aoc23/day14/exampleInput.txt")
        assertThat(input.toPlatform().visualize()).isEqualTo("""O....#....
O.OO#....#
.....##...
OO.#O....O
.O.....O#.
O.#..O.#.#
..O..#O..O
.......O..
#....###..
#OO..#....""".trimIndent())
    }

    @Test
    fun `full tilt north`() {
        val input = readFile("aoc23/day14/exampleInput.txt")
        assertThat(input.toPlatform().tiltTo().visualize()).isEqualTo("""OOOO.#.O..
OO..#....#
OO..O##..O
O..#.OO...
........#.
..#....#.#
..O..#.O.O
..O.......
#....###..
#....#....""".trimIndent())
    }

}
