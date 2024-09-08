package com.kserno.o2interview.card.data

import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.card.domain.CardRepository
import com.kserno.o2interview.persistence.domain.PersistenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_CARD = "KEY_CARD"

@Singleton
class CardPersistedRepositoryImpl @Inject constructor(
    private val persistenceRepository: PersistenceRepository,
) : CardRepository {
    override var card: Card
        get() {
            return decodeString(persistenceRepository.getString(KEY_CARD, null).orEmpty()) ?: Card.NotScratchedCard
        }
        set(value) {
            persistenceRepository.putString(KEY_CARD, Json.encodeToString(value))
        }

    override fun observeCard(): Flow<Card> {
        return persistenceRepository.observeString(KEY_CARD)
            .map { decodeString(it.orEmpty()) ?: Card.NotScratchedCard }
            .onStart { emit(card) }
    }

    private fun decodeString(string: String): Card? {
        return kotlin.runCatching { Json.decodeFromString<Card>(string) }
            .getOrNull()
    }
}
