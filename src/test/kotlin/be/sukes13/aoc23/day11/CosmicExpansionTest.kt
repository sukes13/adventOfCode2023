package be.sukes13.aoc23.day11

import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class CosmicExpansionTest {
    @Test
    fun `parse input`() {
        val input = readFile("aoc23/day11/exampleInput.txt")
        assertThat(input.toUniverse().visualize()).isEqualTo("""...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....""".trimIndent())
    }

    @Test
    fun `expand universe`() {
        val input = readFile("aoc23/day11/exampleInput.txt")
        assertThat(input.toUniverse().expand().visualize()).isEqualTo(
"""....#........
.........#...
#............
.............
.............
........#....
.#...........
............#
.............
.............
.........#...
#....#.......""".trimIndent())
    }

}
