package com.kserno.o2interview.scratch.ui

import android.util.Log
import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.kserno.o2interview.R
import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.card.domain.ObserveCardUseCase
import com.kserno.o2interview.card.ui.CardUiMapper
import com.kserno.o2interview.core.ResourceResolver
import com.kserno.o2interview.core.exception.ExceptionUiMapper
import com.kserno.o2interview.core.invoke
import com.kserno.o2interview.scratch.domain.ScratchCardUseCase
import com.kserno.o2interview.testutil.MainDispatcherRule
import com.kserno.o2interview.ui.CardState
import com.kserno.o2interview.ui.SnackbarEvent
import com.kserno.o2interview.ui.asResourceString
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ScratchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val observeCardUseCase: ObserveCardUseCase = mockk()
    private val scratchCardUseCase: ScratchCardUseCase = mockk()
    private val cardUiMapper: CardUiMapper = mockk()
    private val exceptionUiMapper: ExceptionUiMapper = mockk()
    private val resourceResolver: ResourceResolver = mockk()

    private lateinit var viewModel: ScratchViewModel

    private fun createViewModel() {
        viewModel = ScratchViewModel(
            observeCard = observeCardUseCase,
            scratchCard = scratchCardUseCase,
            cardUiMapper = cardUiMapper,
            exceptionUiMapper = exceptionUiMapper,
            resourceResolver = resourceResolver,
        )
    }

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun `When observeCard emits Then emit correct state`() = runTest {
        val card = mockk<Card>()
        val cardState = mockk<CardState>()
        every { observeCardUseCase.invoke() } returns flowOf(card)
        every { cardUiMapper.map(card) } returns cardState

        createViewModel()

        viewModel.state.test {
            val item = awaitItem() as State.Content

            item.cardState shouldBe cardState
            item.scratchButton.text shouldBe R.string.scratch__scratch_button_text.asResourceString()
        }
    }

    @Test
    fun `Given scratch is success When user clicks scratch Then show success snackbar`() = runTest {
        coEvery { scratchCardUseCase() } returns Unit.right()
        every { observeCardUseCase.invoke() } returns emptyFlow()
        every { resourceResolver.getString(R.string.scratch__scratched_success) } returns "text"
        createViewModel()

        viewModel.onAction(Action.ScratchClick)

        coVerify { scratchCardUseCase.invoke() }
        viewModel.snackbarFlow.test {
            awaitItem() shouldBe SnackbarEvent.InfoSnackbar("text")
        }
    }

    @Test
    fun `Given scratching Then show loading correctly`() = runTest {
        every { observeCardUseCase.invoke() } returns emptyFlow()
        coEvery { scratchCardUseCase.invoke() } returns Unit.right()
        every { resourceResolver.getString(R.string.scratch__scratched_success) } returns "text"
        createViewModel()

        viewModel.loadingState.test {
            awaitItem() shouldBe false
            viewModel.onAction(Action.ScratchClick)
            awaitItem() shouldBe true
            awaitItem() shouldBe false
        }
    }

    @Test
    fun `When scratch fails Then show error`() = runTest {
        val error = RuntimeException("Scratch failed")
        val errorSnackbar = mockk<SnackbarEvent.ErrorSnackbar>()
        every { observeCardUseCase.invoke() } returns emptyFlow()
        every { exceptionUiMapper.map(error, any()) } returns errorSnackbar
        coEvery { scratchCardUseCase.invoke() } returns error.left()
        createViewModel()

        viewModel.onAction(Action.ScratchClick)

        viewModel.snackbarFlow.test {
            awaitItem() shouldBe errorSnackbar
        }
    }
}
