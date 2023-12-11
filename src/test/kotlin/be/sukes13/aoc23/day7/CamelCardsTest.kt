package be.sukes13.aoc23.day7

import be.sukes13.aoc23.day7.Card.*
import be.sukes13.aoc23.day7.Hand.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass


class CamelCardsTest {
    @Test
    fun `parse first line of input, correct hand and bid returned`() {
        assertThat("32T3K 765".toHandAndBid()).isEqualTo(
            HandAndBid(
                listOf(THREE, TWO, TEN, THREE, KING).toHand(),
                765
            )
        )
    }
    
    @Test
    fun `list of cards is a 5 of a kind hand`() {
        assertOfHandType(listOf(JOKER, JOKER, JOKER, JOKER, JOKER), FiveOfAKind::class)
        assertNotOfHandType(listOf(JOKER, JOKER, QUEEN, JOKER, JOKER), FiveOfAKind::class)
    }

    @Test
    fun `list of cards is a 4 of a kind hand`() {
        assertOfHandType(listOf(JOKER, JOKER, QUEEN, JOKER, JOKER), FourOfAKind::class)
        assertNotOfHandType(listOf(JOKER, JOKER, JOKER, JOKER, JOKER), FourOfAKind::class)
        assertNotOfHandType(listOf(JOKER, JOKER, QUEEN, KING, JOKER), FourOfAKind::class)
        assertNotOfHandType(listOf(JOKER, JOKER, JOKER, KING, KING), FourOfAKind::class)
    }

    @Test
    fun `list of cards is a full house`() {
        assertOfHandType(listOf(JOKER, JOKER, JOKER, KING, KING), FullHouse::class)
        assertNotOfHandType(listOf(JOKER, JOKER, JOKER, JOKER, JOKER), FullHouse::class)
        assertNotOfHandType(listOf(JOKER, JOKER, QUEEN, JOKER, JOKER), FullHouse::class)
        assertNotOfHandType(listOf(JOKER, JOKER, QUEEN, KING, JOKER), FullHouse::class)
    }

    @Test
    fun `list of cards is a three of a kind`() {
        assertOfHandType(listOf(JOKER, JOKER, QUEEN, KING, JOKER), ThreeOfAKind::class)
        assertNotOfHandType(listOf(JOKER, JOKER, JOKER, JOKER, JOKER), ThreeOfAKind::class)
        assertNotOfHandType(listOf(JOKER, JOKER, QUEEN, JOKER, JOKER), ThreeOfAKind::class)
        assertNotOfHandType(listOf(JOKER, JOKER, JOKER, KING, KING), ThreeOfAKind::class)
    }

    @Test
    fun `list of cards is a two pair`() {
        assertOfHandType(listOf(JOKER, QUEEN, JOKER, KING, QUEEN), TwoPair::class)
        assertNotOfHandType(listOf(JOKER, JOKER, JOKER, JOKER, JOKER), TwoPair::class)
        assertNotOfHandType(listOf(JOKER, JOKER, QUEEN, JOKER, JOKER), TwoPair::class)
        assertNotOfHandType(listOf(JOKER, JOKER, QUEEN, KING, JOKER), TwoPair::class)
        assertNotOfHandType(listOf(JOKER, JOKER, JOKER, KING, KING), TwoPair::class)
    }

    @Test
    fun `list of cards is a one pair`() {
        assertOfHandType(listOf(JOKER, QUEEN, ACE, KING, QUEEN), OnePair::class)
        assertNotOfHandType(listOf(JOKER, JOKER, JOKER, JOKER, JOKER), OnePair::class)
        assertNotOfHandType(listOf(JOKER, JOKER, QUEEN, JOKER, JOKER), OnePair::class)
        assertNotOfHandType(listOf(JOKER, JOKER, QUEEN, KING, JOKER), OnePair::class)
        assertNotOfHandType(listOf(JOKER, JOKER, JOKER, KING, KING), OnePair::class)
        assertNotOfHandType(listOf(JOKER, QUEEN, JOKER, KING, QUEEN), OnePair::class)
    }

    private fun assertOfHandType(cards: List<Card>, clazz: KClass<out Hand>) {
        assertThat(cards.toHand()).isInstanceOf(clazz.java)
    }

    private fun assertNotOfHandType(cards: List<Card>, clazz: KClass<out Hand>) {
        assertThat(cards.toHand()).isNotInstanceOf(clazz.java)
    }

    @Test
    fun `with jokers - list of cards is a 5 of a kind hand`() {
        assertOfHandTypeWithJokers(listOf(KING, KING, KING, KING, KING), FiveOfAKind::class)
        assertOfHandTypeWithJokers(listOf(JOKER, JOKER, JOKER, JOKER, JOKER), FiveOfAKind::class)
        assertOfHandTypeWithJokers(listOf(JOKER, JOKER, QUEEN, JOKER, JOKER), FiveOfAKind::class)
        assertOfHandTypeWithJokers(listOf(QUEEN, JOKER, QUEEN, QUEEN, QUEEN), FiveOfAKind::class)
        assertOfHandTypeWithJokers(listOf(JOKER, JOKER, JOKER, JOKER, JOKER), FiveOfAKind::class)
        assertOfHandTypeWithJokers(listOf(JOKER, JOKER, QUEEN, QUEEN, JOKER), FiveOfAKind::class)
        assertOfHandTypeWithJokers(listOf(QUEEN, JOKER, QUEEN, JOKER, QUEEN), FiveOfAKind::class)
        assertOfNotHandTypeWithJokers(listOf(QUEEN, JOKER, QUEEN, QUEEN, KING), FiveOfAKind::class)
    }

    @Test
    fun `with jokers - list of cards is a 4 of a kind hand`() {
        assertOfHandTypeWithJokers(listOf(QUEEN, JOKER, QUEEN, QUEEN, KING), FourOfAKind::class)
        assertOfHandTypeWithJokers(listOf(JOKER, JOKER, QUEEN, JOKER, KING), FourOfAKind::class)
        assertOfHandTypeWithJokers(listOf(JOKER, QUEEN, QUEEN, JOKER, KING), FourOfAKind::class)
        assertOfNotHandTypeWithJokers(listOf(ACE, QUEEN, QUEEN, JOKER, KING), FourOfAKind::class)
    }

    @Test
    fun `with jokers - list of cards is a full house`() {
        assertOfHandTypeWithJokers(listOf(ACE, ACE, QUEEN, QUEEN, JOKER), FullHouse::class)
        assertOfHandTypeWithJokers(listOf(ACE, ACE, ACE, QUEEN, QUEEN), FullHouse::class)
        assertOfNotHandTypeWithJokers(listOf(ACE, QUEEN, QUEEN, JOKER, KING), FullHouse::class)
    }

    @Test
    fun `with jokers - list of cards is a 3 of a kind`() {
        assertOfHandTypeWithJokers(listOf(ACE, ACE, ACE, QUEEN, KING), ThreeOfAKind::class)
        assertOfHandTypeWithJokers(listOf(JOKER, ACE, ACE, KING, QUEEN), ThreeOfAKind::class)
        assertOfHandTypeWithJokers(listOf(JOKER, JOKER, ACE, KING, QUEEN), ThreeOfAKind::class)
        assertOfNotHandTypeWithJokers(listOf(ACE, TEN, QUEEN, JOKER, KING), ThreeOfAKind::class)
    }

    @Test
    fun `with jokers - list of cards is a 2 pair`() {
        assertOfHandTypeWithJokers(listOf(QUEEN, ACE, ACE, QUEEN, KING), TwoPair::class)
    }

    @Test
    fun `with jokers - list of cards is a 1 pair`() {
        assertOfHandTypeWithJokers(listOf(QUEEN, TEN, ACE, QUEEN, KING), OnePair::class)
        assertOfHandTypeWithJokers(listOf(JOKER, TEN, ACE, QUEEN, KING), OnePair::class)
    }

    private fun assertOfHandTypeWithJokers(cards: List<Card>, clazz: KClass<out Hand>) {
        assertThat(cards.toHand().upgradeForJokers()).isInstanceOf(clazz.java)
    }

    private fun assertOfNotHandTypeWithJokers(cards: List<Card>, clazz: KClass<out Hand>) {
        assertThat(cards.toHand().upgradeForJokers()).isNotInstanceOf(clazz.java)
    }

}
