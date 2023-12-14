package be.sukes13.aoc23.day14

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class ParabolicReflectorDishTest {
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
        assertThat(input.toPlatform().tiltedTo().visualize()).isEqualTo("""OOOO.#.O..
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

    @Test
    fun `one cycle`() {
        val input = readFile("aoc23/day14/exampleInput.txt")
        assertThat(input.toPlatform().oneCycle().visualize()).isEqualTo(""".....#....
....#...O#
...OO##...
.OO#......
.....OOO#.
.O#...O#.#
....O#....
......OOOO
#...O###..
#..OO#....""".trimIndent())
    }

    @Test
    fun `three cycles`() {
        val input = readFile("aoc23/day14/exampleInput.txt")
        assertThat(input.toPlatform().oneCycle().oneCycle().oneCycle().visualize()).isEqualTo(""".....#....
....#...O#
.....##...
..O#......
.....OOO#.
.O#...O#.#
....O#...O
.......OOO
#...O###.O
#.OOO#...O""".trimIndent())
    }

}
