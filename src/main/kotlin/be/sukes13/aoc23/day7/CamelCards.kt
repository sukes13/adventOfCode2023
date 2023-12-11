package be.sukes13.aoc23.day7

import be.sukes13.aoc23.day7.Card.JOKER
import be.sukes13.aoc23.day7.Hand.*
import be.sukes13.mapLines
import kotlin.reflect.KClass

fun part1(input: String) = input.mapLines { it.toHandAndBid() }
    .sortedWith(HandComparator { it.weight })
    .calculateTotalWinnings()

fun part2(input: String) = input.mapLines { it.toHandAndBid() }
    .map { it.upgradeForJokers() }
    .sortedWith(HandComparator { it.weightWithJoker })
    .calculateTotalWinnings()

internal fun String.toHandAndBid() =
    split(" ").let { line ->
        HandAndBid(
            hand = line.first().map { Card.fromChar(it) }.toHand(),
            bid = line.last().toInt()
        )
    }

internal fun List<Card>.toHand() = when {
    groupBy { it.code }.containsSameCardInAmount(5) -> FiveOfAKind(this)
    groupBy { it.code }.containsSameCardInAmount(4) -> FourOfAKind(this)
    groupBy { it.code }.let { it.containsSameCardInAmount(3) && it.containsSameCardInAmount(2) } -> FullHouse(this)
    groupBy { it.code }.containsSameCardInAmount(3) -> ThreeOfAKind(this)
    groupBy { it.code }.filter { it.value.size == 2 }.size == 2 -> TwoPair(this)
    groupBy { it.code }.size == 4 -> OnePair(this)
    else -> HighCard(this)
}

private fun List<HandAndBid>.calculateTotalWinnings() =
    mapIndexed { index, handAndBid -> (index + 1) * handAndBid.bid }.sum()

data class HandAndBid(val hand: Hand, val bid: Int) {
    fun upgradeForJokers() = copy(hand = hand.upgradeForJokers())
}

sealed interface Hand {
    val cards: List<Card>

    fun upgradeForJokers() =
        when (this) {
            is FiveOfAKind -> this
            is FourOfAKind -> if (cards.contains(JOKER)) FiveOfAKind(cards) else this
            is FullHouse -> if (cards.contains(JOKER)) FiveOfAKind(cards) else this
            is ThreeOfAKind -> if (cards.contains(JOKER)) FourOfAKind(cards) else this
            is TwoPair -> when {
                cards.count { it == JOKER } == 1 -> FullHouse(cards)
                cards.count { it == JOKER } == 2 -> FourOfAKind(cards)
                else -> this
            }

            is OnePair -> if (cards.count { it == JOKER }.let { it == 1 || it == 2 }) ThreeOfAKind(cards) else this
            is HighCard -> if (cards.contains(JOKER)) OnePair(cards) else this
        }

    data class FiveOfAKind(override val cards: List<Card>) : Hand
    data class FourOfAKind(override val cards: List<Card>) : Hand
    data class FullHouse(override val cards: List<Card>) : Hand
    data class ThreeOfAKind(override val cards: List<Card>) : Hand
    data class TwoPair(override val cards: List<Card>) : Hand
    data class OnePair(override val cards: List<Card>) : Hand
    data class HighCard(override val cards: List<Card>) : Hand
}

class HandComparator(val weightSelector: (Card) -> (Int)) : Comparator<HandAndBid> {
    override fun compare(handAndBidOne: HandAndBid, handAndBidTwo: HandAndBid) =
        if (handAndBidOne.hand::class != handAndBidTwo.hand::class) {
            handAndBidOne.hand.compareByHandType(handAndBidTwo.hand)
        } else {
            handAndBidOne.hand.compareByCardValues(handAndBidTwo.hand)
        }

    private fun Hand.compareByHandType(handTwo: Hand) =
        handRegistry[this::class]!!.compareTo(handRegistry[handTwo::class]!!)

    private fun Hand.compareByCardValues(handTwo: Hand, index: Int = 0): Int =
        weightSelector(cards[index]).compareTo(weightSelector(handTwo.cards[index])).let {
            if (it == 0) compareByCardValues(handTwo, index + 1) else it
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

enum class Card(val code: Char, val weight: Int, val weightWithJoker: Int) {
    TWO('2', 1, 1),
    THREE('3', 2, 2),
    FOUR('4', 3, 3),
    FIVE('5', 4, 4),
    SIX('6', 5, 5),
    SEVEN('7', 6, 6),
    EIGHT('8', 7, 7),
    NINE('9', 8, 8),
    TEN('T', 9, 9),
    JOKER('J', 10, 0),
    QUEEN('Q', 11, 11),
    KING('K', 12, 12),
    ACE('A', 13, 13), ;

    companion object {
        fun fromChar(char: Char) = values().single { it.code == char }
    }
}

private fun Map<Char, List<Card>>.containsSameCardInAmount(amount: Int) =
    filter { it.value.size == amount }.isNotEmpty()