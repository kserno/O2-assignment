package com.kserno.o2interview.scratch.domain

import android.util.Log
import arrow.core.left
import arrow.core.right
import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.card.domain.CardRepository
import com.kserno.o2interview.core.invoke
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ScratchCardUseCaseTest {

    private val cardRepository: CardRepository = mockk(relaxUnitFun = true)
    private val scratchCodeGenerator: ScratchCodeGenerator = mockk()

    private val uc = ScratchCardUseCase(cardRepository = cardRepository, scratchCodeGenerator = scratchCodeGenerator)

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.w(any(), any(), any()) } returns 0
    }

    @Test
    fun `Given scratch generator throws Then return exception`() = runTest {
        val t = Throwable()
        every { scratchCodeGenerator.generateCode() } throws t

        val result = uc.invoke()

        result shouldBe t.left()
    }

    @Test
    fun `Given scratch generator returns code Then change card state`() = runTest {
        val code = "code"
        every { scratchCodeGenerator.generateCode() } returns code

        val result = uc.invoke()

        result shouldBe Unit.right()
        verify { cardRepository.card = Card.ScratchedCard(code) }
    }
}
