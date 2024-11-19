package kr.ac.kumoh.ce.s20240000.s24w11carddealer

import kotlin.random.Random

class CardModel {
    companion object {
        const val TOTAL_CARDS = 52
        const val NUMBER_OF_CARDS = 5
    }

    private val _cards = mutableSetOf<Int>()
//    public val cards: Set<Int>
//        get() = _cards

    fun dealCards(n: Int = NUMBER_OF_CARDS): Set<Int> {
        _cards.clear()

        while (_cards.size < n) {
            _cards.add(Random.nextInt(TOTAL_CARDS))
        }

        return _cards
    }
}