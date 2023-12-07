package be.sukes13.aoc23.day7

import be.sukes13.aoc23.day7.Hand.*
import be.sukes13.mapLines
import kotlin.reflect.KClass

fun part1(input: String) = input.mapLines { it.toHandAndBid() }
    .sortedWith(HandComparator())
    .mapIndexed { index, handAndBid -> (index + 1) * handAndBid.bid }
    .sum()


fun part2(input: String) = input.mapLines { it.toHandAndBid().also { println(it) } }
    .sortedWith(HandComparator())
    .mapIndexed { index, handAndBid -> (index + 1) * handAndBid.bid }
    .sum()


sealed interface Hand {
    val cards: List<Card>

    fun upgradeIfJoker(): Hand {
        return when (this) {
            is FiveOfAKind -> this
            is FourOfAKind -> if (cards.groupBy { it.code }
                    .minBy { it.value.size }.value.first() == Card.JACK) FiveOfAKind(cards) else this

            is FullHouse -> if (cards.groupBy { it.code }.minBy { it.value.size }.value.first() == Card.JACK) {
                if (cards.count { it == Card.JACK } == 1) FourOfAKind(cards)
                else FiveOfAKind(cards)
            } else this

            is ThreeOfAKind -> if (cards.groupBy { it.code }.minBy { it.value.size }.value.first() == Card.JACK) {
                if (cards.count { it == Card.JACK } == 1) FourOfAKind(cards)
                else FiveOfAKind(cards)
            } else this

            is TwoPair -> this
            is OnePair -> this
            is HighCard -> this
        }
    }

    data class FiveOfAKind(override val cards: List<Card>) : Hand
    data class FourOfAKind(override val cards: List<Card>) : Hand
    data class FullHouse(override val cards: List<Card>) : Hand
    data class ThreeOfAKind(override val cards: List<Card>) : Hand
    data class TwoPair(override val cards: List<Card>) : Hand
    data class OnePair(override val cards: List<Card>) : Hand
    data class HighCard(override val cards: List<Card>) : Hand
}


class HandComparator : Comparator<HandAndBid> {
    override fun compare(handAndBidOne: HandAndBid, handAndBidTwo: HandAndBid) =
        if (handAndBidOne.hand::class != handAndBidTwo.hand::class) {
            handRegistry[handAndBidOne.hand::class]!!.compareTo(handRegistry[handAndBidTwo.hand::class]!!)
        } else {
            handAndBidOne.compareByCardValues(handAndBidTwo)
        }

    private fun HandAndBid.compareByCardValues(handTwo: HandAndBid, index: Int = 0): Int =
        hand.cards[index].weight.compareTo(handTwo.hand.cards[index].weight).let {
            if (it == 0) this.compareByCardValues(handTwo, index + 1) else it
        }

    private val handRegistry: Map<KClass<out Hand>, Int> = mapOf(
        FiveOfAKind::class to 7,
        FourOfAKind::class to 6,
        FullHouse::class to 5,
        ThreeOfAKind::class to 4,
        TwoPair::class to 3,
        OnePair::class to 2,
        HighCard::class to 1,
    )

}

internal fun List<Card>.isOnePair() =
    groupBy { it.code }.let { groupedCardMap ->
        groupedCardMap.size == 4 && groupedCardMap.containsCardAmount(2)
    }

internal fun List<Card>.isTwoPair() =
    groupBy { it.code }.filter { it.value.size == 2 }.size == 2

internal fun List<Card>.isThreeOfAKind() =
    groupBy { it.code }.let { groupedCardMap ->
        groupedCardMap.size == 3 && groupedCardMap.containsCardAmount(3)
    }

internal fun List<Card>.isFullHouse() =
    groupBy { it.code }.let { groupedCardMap ->
        groupedCardMap.containsCardAmount(3) && groupedCardMap.containsCardAmount(2)
    }

internal fun List<Card>.isFourOfAKind() = groupBy { it.code }.containsCardAmount(4)

internal fun List<Card>.isFiveOfAKind() = groupBy { it.code }.containsCardAmount(5)

private fun Map<Char, List<Card>>.containsCardAmount(amount: Int) = filter { it.value.size == amount }.isNotEmpty()


internal fun String.toHandAndBid() =
    split(" ").let { line ->
        HandAndBid(
            hand = line.first().map { Card.fromChar(it) }.toHandType(),
            bid = line.last().toInt()
        )
    }


internal fun List<Card>.toHandType() = when {
    isFiveOfAKind() -> FiveOfAKind(this)
    isFourOfAKind() -> FourOfAKind(this)
    isFullHouse() -> FullHouse(this)
    isThreeOfAKind() -> ThreeOfAKind(this)
    isTwoPair() -> TwoPair(this)
    isOnePair() -> OnePair(this)
    else -> HighCard(this)
}.upgradeIfJoker()

data class HandAndBid(val hand: Hand, val bid: Int)

enum class Card(val code: Char, val weight: Int, val weightPart2: Int) {
    TWO('2', 1, 1),
    THREE('3', 2, 2),
    FOUR('4', 3, 3),
    FIVE('5', 4, 4),
    SIX('6', 5, 5),
    SEVEN('7', 6, 6),
    EIGHT('8', 7, 7),
    NINE('9', 8, 8),
    TEN('T', 9, 9),
    JACK('J', 10, 0),
    QUEEN('Q', 11, 11),
    KING('K', 12, 12),
    ACE('A', 13, 13), ;

    companion object {
        fun fromChar(char: Char) = values().single { it.code == char }
    }
}

