package kr.ac.kumoh.ce.s20240000.s24w11carddealer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CardViewModel : ViewModel() {
    private val cardModel = CardModel()
    private val _cards = MutableLiveData<List<String>>(
        listOf(
            "c_10_of_spades",
            "c_jack_of_spades2",
            "c_queen_of_spades2",
            "c_king_of_spades2",
            "c_ace_of_spades",
        )
    )
    val cards: LiveData<List<String>>
        get() = _cards

    fun shuffle() {
        val newCards = cardModel.dealCards()

        _cards.value = newCards.sorted().map { getCardName(it) }
    }

    private fun getCardName(c: Int): String {
        val shape = when (c / 13) {
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }

        val number = when (c % 13) {
            0 -> "ace"
            in 1..9 -> (c % 13 + 1).toString()
            10 -> "jack"
            11 -> "queen"
            12 -> "king"
            else -> "error"
        }

        return if (c % 13 in 10..12)
            "c_${number}_of_${shape}2"
        else
            "c_${number}_of_${shape}"
    }
}