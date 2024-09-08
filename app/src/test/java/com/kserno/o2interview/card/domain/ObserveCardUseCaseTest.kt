package com.kserno.o2interview.card.domain

import app.cash.turbine.test
import com.kserno.o2interview.core.invoke
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ObserveCardUseCaseTest {

    private val cardRepository: CardRepository = mockk()

    private val uc = ObserveCardUseCase(cardRepository)

    @Test
    fun `Given card repository observe Then return same value`() = runTest {
        every { cardRepository.observeCard() } returns flowOf(Card.NotScratchedCard, Card.ScratchedCard("code"), Card.ActivatedCard("code2"))

        uc.invoke().test {
            awaitItem() shouldBe Card.NotScratchedCard
            awaitItem() shouldBe Card.ScratchedCard("code")
            awaitItem() shouldBe Card.ActivatedCard("code2")
            awaitComplete()
        }
    }
}
