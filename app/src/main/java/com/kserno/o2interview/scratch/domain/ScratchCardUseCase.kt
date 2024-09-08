package com.kserno.o2interview.scratch.domain

import android.util.Log
import arrow.core.Either
import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.card.domain.CardRepository
import com.kserno.o2interview.core.SuspendUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

private val LONG_OPERATION_DELAY = 5.seconds

class ScratchCardUseCase @Inject constructor(
    private val cardRepository: CardRepository,
    private val scratchCodeGenerator: ScratchCodeGenerator,
) : SuspendUseCase<Unit, Either<Throwable, Unit>> {

    override suspend fun invoke(input: Unit): Either<Throwable, Unit> {
        return Either.catch {
            delay(LONG_OPERATION_DELAY)
            val code = scratchCodeGenerator.generateCode()
            cardRepository.card = Card.ScratchedCard(code)
        }
            .onLeft { Log.w(ScratchCardUseCase::class.simpleName, "Scratch card failed", it) }
    }
}
