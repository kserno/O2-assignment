package com.kserno.o2interview.card.domain

import android.util.Log
import com.kserno.o2interview.core.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class ObserveCardUseCase @Inject constructor(
    private val cardRepository: CardRepository,
) : UseCase<Unit, Flow<Card>> {
    override fun invoke(input: Unit): Flow<Card> {
        return cardRepository.observeCard()
            .catch {
                Log.w(ObserveCardUseCase::class.simpleName, "Failed to observe card", it)
                emit(Card.NotScratchedCard)
            }
    }
}
