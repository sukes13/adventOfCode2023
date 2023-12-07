package be.sukes13.aoc23.day7

import be.sukes13.aoc23.day7.Card.*
import be.sukes13.aoc23.day7.Hand.*
import be.sukes13.mapLines
import be.sukes13.readFile
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


class Day7Test {
    @Test
    fun `parse first line of input, correct hand and bid returned`() {
        assertThat("32T3K 765".toHandAndBid()).isEqualTo(
            HandAndBid(
                listOf(THREE, TWO, TEN, THREE, KING).toHandType(),
                765
            )
        )
    }
    
    @Test
    fun `list of cards is a 5 of a kind hand`() {
        assertThat(listOf(JACK, JACK, JACK, JACK, JACK).isFiveOfAKind()).isTrue
        assertThat(listOf(JACK, JACK, QUEEN, JACK, JACK).isFiveOfAKind()).isFalse
    }

    @Test
    fun `list of cards is a 4 of a kind hand`() {
        assertThat(listOf(JACK, JACK, JACK, JACK, JACK).isFourOfAKind()).isFalse
        assertThat(listOf(JACK, JACK, QUEEN, JACK, JACK).isFourOfAKind()).isTrue
        assertThat(listOf(JACK, JACK, QUEEN, KING, JACK).isFourOfAKind()).isFalse
        assertThat(listOf(JACK, JACK, JACK, KING, KING).isFourOfAKind()).isFalse
    }

    @Test
    fun `list of cards is a full house`() {
        assertThat(listOf(JACK, JACK, JACK, JACK, JACK).isFullHouse()).isFalse
        assertThat(listOf(JACK, JACK, QUEEN, JACK, JACK).isFullHouse()).isFalse
        assertThat(listOf(JACK, JACK, QUEEN, KING, JACK).isFullHouse()).isFalse
        assertThat(listOf(JACK, JACK, JACK, KING, KING).isFullHouse()).isTrue
    }

    @Test
    fun `list of cards is a three of a kind`() {
        assertThat(listOf(JACK, JACK, JACK, JACK, JACK).isThreeOfAKind()).isFalse
        assertThat(listOf(JACK, JACK, QUEEN, JACK, JACK).isThreeOfAKind()).isFalse
        assertThat(listOf(JACK, JACK, QUEEN, KING, JACK).isThreeOfAKind()).isTrue
        assertThat(listOf(JACK, JACK, JACK, KING, KING).isThreeOfAKind()).isFalse
    }

    @Test
    fun `list of cards is a two pair`() {
        assertThat(listOf(JACK, JACK, JACK, JACK, JACK).isTwoPair()).isFalse
        assertThat(listOf(JACK, JACK, QUEEN, JACK, JACK).isTwoPair()).isFalse
        assertThat(listOf(JACK, JACK, QUEEN, KING, JACK).isTwoPair()).isFalse
        assertThat(listOf(JACK, JACK, JACK, KING, KING).isTwoPair()).isFalse
        assertThat(listOf(JACK, QUEEN, JACK, KING, QUEEN).isTwoPair()).isTrue
    }

    @Test
    fun `list of cards is a one pair`() {
        assertThat(listOf(JACK, JACK, JACK, JACK, JACK).isOnePair()).isFalse
        assertThat(listOf(JACK, JACK, QUEEN, JACK, JACK).isOnePair()).isFalse
        assertThat(listOf(JACK, JACK, QUEEN, KING, JACK).isOnePair()).isFalse
        assertThat(listOf(JACK, JACK, JACK, KING, KING).isOnePair()).isFalse
        assertThat(listOf(JACK, QUEEN, JACK, KING, QUEEN).isOnePair()).isFalse
        assertThat(listOf(JACK, QUEEN, ACE, KING, QUEEN).isOnePair()).isTrue
    }

    @ParameterizedTest
    @MethodSource("cardTypesTest")
    fun `given a list of cards, correct handType is returned`(
        hand: List<Card>, handType: Class<Hand>
    ) {
        assertThat(hand.toHandType()).isInstanceOf(handType)
    }

    companion object {
        @JvmStatic
        fun cardTypesTest(): Stream<Arguments> = Stream.of(
            Arguments.of(listOf(JACK, JACK, JACK, JACK, JACK), FiveOfAKind::class.java),
            Arguments.of(listOf(JACK, JACK, QUEEN, JACK, JACK), FourOfAKind::class.java),
            Arguments.of(listOf(JACK, JACK, QUEEN, QUEEN, JACK), FullHouse::class.java),
            Arguments.of(listOf(JACK, JACK, QUEEN, JACK, KING), ThreeOfAKind::class.java),
            Arguments.of(listOf(JACK, JACK, QUEEN, KING, QUEEN), TwoPair::class.java),
            Arguments.of(listOf(JACK, JACK, QUEEN, KING, ACE), OnePair::class.java),
            Arguments.of(listOf(JACK, TEN, QUEEN, KING, ACE), HighCard::class.java),
        )
    }
}
