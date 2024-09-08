package com.kserno.o2interview.card.data

import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.card.domain.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor() : CardRepository {

    private var _card = MutableStateFlow<Card>(Card.NotScratchedCard)
    override var card: Card
        get() = _card.value
        set(value) {
            _card.value = value
        }

    override fun observeCard(): Flow<Card> {
        return _card
    }
}
