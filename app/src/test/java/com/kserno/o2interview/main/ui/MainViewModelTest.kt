package com.kserno.o2interview.main.ui

import android.util.Log
import app.cash.turbine.test
import com.kserno.o2interview.R
import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.card.domain.ObserveCardUseCase
import com.kserno.o2interview.card.ui.CardUiMapper
import com.kserno.o2interview.core.invoke
import com.kserno.o2interview.testutil.MainDispatcherRule
import com.kserno.o2interview.ui.CardState
import com.kserno.o2interview.ui.asResourceString
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val observeCardUseCase: ObserveCardUseCase = mockk()
    private val cardUiMapper: CardUiMapper = mockk()

    private lateinit var viewModel: MainViewModel

    private fun createViewModel() {
        viewModel = MainViewModel(observeCardUseCase, cardUiMapper)
    }

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun `When observe returns Then emit correct state`() = runTest {
        val card = mockk<Card>()
        val cardState = mockk<CardState>()
        every { observeCardUseCase.invoke() } returns flowOf(card)
        every { cardUiMapper.map(card) } returns cardState

        createViewModel()

        viewModel.state.test {
            val item = awaitItem() as State.Content

            item.cardState shouldBe cardState
            item.scratchButtonState.text shouldBe R.string.main__scratch_button_title.asResourceString()
            item.activationButtonState.text shouldBe R.string.main__activation_button_title.asResourceString()
        }
    }

    @Test
    fun `When user clicks activate Then go to activate`() = runTest {
        every { observeCardUseCase.invoke() } returns emptyFlow()
        createViewModel()

        viewModel.onAction(Action.ActivationClick)

        viewModel.event.test {
            awaitItem() shouldBe Event.GoToActivation
        }
    }

    @Test
    fun `When user clicks scratch Then go to scratch`() = runTest {
        every { observeCardUseCase.invoke() } returns emptyFlow()
        createViewModel()

        viewModel.onAction(Action.ScratchClick)

        viewModel.event.test {
            awaitItem() shouldBe Event.GoToScratch
        }
    }
}
