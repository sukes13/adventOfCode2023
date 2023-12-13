package be.sukes13.aoc23.day4

import be.sukes13.mapLines

fun part1(input: String) = input.mapLines { it.toScratchcards() }.sumOf { it.worth }

fun part2(input: String) = input.mapLines { it.toScratchcards() }.scratch()

private fun String.toScratchcards() = split(" | ").let { line ->
    Scratchcard(
        number = line.first().replace("Card ", "").split(":").first().trim().toInt(),
        winning = line.first().split(":").last().parseNumbers(),
        owned = line.last().parseNumbers()
    )
}

private fun String.parseNumbers() = split(" ").filter(String::isNotBlank).map { it.toInt() }

data class Scratchcard(
    val number: Int,
    private val winning: List<Int>,
    private val owned: List<Int>,
) {
    val worth = owned.filter { it in winning }.fold(1) { acc, _ -> acc + acc } / 2
    val matching = owned.count { it in winning }
}

private tailrec fun List<Scratchcard>.scratch(
    cardsToScratch: List<Scratchcard> = this,
    totalCards: Int = 0
): Int {
    val newTotal = totalCards + cardsToScratch.size
    cardsToScratch.flatMap { newCardsWon(it) }.let { wonCards ->
        return if (wonCards.isEmpty()) newTotal
        else scratch(wonCards, newTotal)
    }
}

private fun List<Scratchcard>.newCardsWon(card: Scratchcard) =
    filter { it.number in (card.number + 1 until (card.number + card.matching + 1)) }



