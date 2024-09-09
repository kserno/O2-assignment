package com.kserno.o2interview.activation.domain

import android.util.Log
import arrow.core.Either
import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.card.domain.CardRepository
import com.kserno.o2interview.core.SuspendUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import javax.inject.Inject

private const val CUTOFF_VALUE = 277028

class ActivateCardUseCase @Inject constructor(
    private val activationRepository: ActivationRepository,
    private val cardRepository: CardRepository,
) : SuspendUseCase<Unit, Either<Throwable, Unit>> {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override suspend fun invoke(input: Unit): Either<Throwable, Unit> {
        val deferred = scope.async {
            Either.catch {
                val code = when (val card = cardRepository.card) {
                    is Card.ActivatedCard -> throw CardAlreadyActivatedException()
                    is Card.NotScratchedCard -> throw CardNotScratchedException()
                    is Card.ScratchedCard -> card.code
                }

                val activationCode = activationRepository.activateCode(code)
//                delay(10_000L) // for testing the async activation
                if (activationCode.android <= CUTOFF_VALUE) {
                    throw CardActivationCodeTooLowException()
                } else {
                    cardRepository.card = Card.ActivatedCard(code)
                }
            }
                .onLeft { Log.w(ActivateCardUseCase::class.simpleName, "Error activating card", it) }
        }

        return deferred.await()
    }
}

data class CardNotScratchedException(override val message: String? = null) : Throwable(message)
data class CardAlreadyActivatedException(override val message: String? = null) : Throwable(message)
data class CardActivationCodeTooLowException(override val message: String? = null) : Throwable(message)
