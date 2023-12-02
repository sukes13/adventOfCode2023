package be.sukes13.aoc23.day1

import be.sukes13.mapLines

fun part1(input: String) = input.mapLines { line ->
    line.filter { it.isDigit() }.let {
        concatToInt(it.first(), it.last())
    }
}.sum()

fun part2(input: String) = input.mapLines { line ->
    concatToInt(
        line.replaceFirstNumberStringWithInteger().first { it.isDigit() },
        line.replaceLastNumberStringWithInteger().last { it.isDigit() }
    )
}.sum()


private fun concatToInt(firstDigit: Char, lastDigit: Char) = "$firstDigit$lastDigit".toInt()

private fun String.replaceFirstNumberStringWithInteger() =
    findAnyOf(NumberStrings.names(), 0, true)
        ?.let { (_, number) -> replaceNumberStringWithInteger(number) }
        ?: this

private fun String.replaceLastNumberStringWithInteger() =
    findLastAnyOf(NumberStrings.names(), length, true)
        ?.let { (_, number) -> replaceNumberStringWithInteger(number) }
        ?: this

private fun String.replaceNumberStringWithInteger(number: String) =
    replace(number, "${NumberStrings.valueOf(number).value}", true)

enum class NumberStrings(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    companion object {
        fun names() = NumberStrings.values().map { it.name }
    }
}