package com.kserno.o2interview.activation.domain

import android.util.Log
import arrow.core.left
import arrow.core.right
import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.card.domain.CardRepository
import com.kserno.o2interview.core.invoke
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
class ActivateCardUseCaseTest {

    private val activationRepository: ActivationRepository = mockk()
    private val cardRepository: CardRepository = mockk(relaxUnitFun = true)

    private val uc = ActivateCardUseCase(
        activationRepository = activationRepository,
        cardRepository = cardRepository,
    )

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.w(any(), any(), any()) } returns 0
    }

    @Test
    fun `Given card not scratched When invoked Then return exception`() = runTest {
        every { cardRepository.card } returns Card.NotScratchedCard

        val result = uc.invoke()

        result shouldBe CardNotScratchedException().left()
    }

    @Test
    fun `Given card activated When invoked Then return exception`() = runTest {
        every { cardRepository.card } returns Card.ActivatedCard("code")

        val result = uc.invoke()

        result shouldBe CardAlreadyActivatedException().left()
    }

    @Test
    fun `Given card scratched When invoked and activation low Then return exception`() = runTest {
        val code = "code"
        every { cardRepository.card } returns Card.ScratchedCard(code)
        coEvery { activationRepository.activateCode(code) } returns ActivationCode(277028)

        val result = uc.invoke()

        result shouldBe CardActivationCodeTooLowException().left()
    }

    @Test
    fun `Given card scratched When invoked and activation correct Then run successfully`() = runTest {
        val code = "code"
        every { cardRepository.card } returns Card.ScratchedCard(code)
        coEvery { activationRepository.activateCode(code) } returns ActivationCode(999999)

        val result = uc.invoke()

        result shouldBe Unit.right()
        verify { cardRepository.card = Card.ActivatedCard(code) }
    }

    @Test
    fun `Given card scratched and activation throws exception When invoked Then return exception`() = runTest {
        val t = Throwable()
        every { cardRepository.card } returns Card.ScratchedCard("code")
        coEvery { activationRepository.activateCode(any()) } throws t

        val result = uc.invoke()
        result shouldBe t.left()
    }
}
