package com.kserno.o2interview.card.domain

import kotlinx.coroutines.flow.Flow

interface CardRepository {
    var card: Card
    fun observeCard(): Flow<Card>
}
