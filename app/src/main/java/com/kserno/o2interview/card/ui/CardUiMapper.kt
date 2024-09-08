package com.kserno.o2interview.card.ui

import com.kserno.o2interview.R
import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.ui.CardState
import com.kserno.o2interview.ui.asPlainString
import javax.inject.Inject

class CardUiMapper @Inject constructor() {

    fun map(card: Card): CardState {
        return when (card) {
            is Card.ActivatedCard -> CardState(backgroundColor = R.color.success, text = card.code.asPlainString())
            is Card.NotScratchedCard -> CardState(backgroundColor = R.color.gray, text = null)
            is Card.ScratchedCard -> CardState(backgroundColor = R.color.yellow, text = card.code.asPlainString())
        }
    }
}
