package com.kserno.o2interview.activation.ui

import android.util.Log
import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.kserno.o2interview.R
import com.kserno.o2interview.activation.domain.ActivateCardUseCase
import com.kserno.o2interview.core.ResourceResolver
import com.kserno.o2interview.core.exception.ExceptionUiMapper
import com.kserno.o2interview.core.invoke
import com.kserno.o2interview.testutil.MainDispatcherRule
import com.kserno.o2interview.ui.SnackbarEvent
import com.kserno.o2interview.ui.asResourceString
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ActivationViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val activateCardUseCase: ActivateCardUseCase = mockk()
    private val exceptionUiMapper: ExceptionUiMapper = mockk()
    private val resourceResolver: ResourceResolver = mockk()

    private lateinit var viewModel: ActivationViewModel

    private fun createViewModel() {
        viewModel = ActivationViewModel(activateCardUseCase, exceptionUiMapper, resourceResolver)
    }

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun `When initialized Then show correct state`() = runTest {
        createViewModel()

        viewModel.state.test {
            awaitItem().activationButton.text shouldBe R.string.activation__activate_button_title.asResourceString()
        }
    }

    @Test
    fun `Given activation successful When activate clicked Then show success snackbar`() = runTest {
        coEvery { activateCardUseCase.invoke() } returns Unit.right()
        every { resourceResolver.getString(R.string.activation__success_snackbar_title) } returns "Success"
        createViewModel()

        viewModel.onAction(Action.ActivateClick)

        viewModel.snackbarFlow.test {
            val item = awaitItem() as SnackbarEvent.SuccessSnackbar
            item.message shouldBe "Success"
        }
    }

    @Test
    fun `Given activation fails When activate clicked Then show error mapped snackbar`() = runTest {
        val throwable = Throwable()
        val errorSnackbar = mockk<SnackbarEvent.ErrorSnackbar>()
        every { exceptionUiMapper.map(throwable, any()) } returns errorSnackbar
        coEvery { activateCardUseCase.invoke() } returns throwable.left()
        createViewModel()

        viewModel.onAction(Action.ActivateClick)

        viewModel.snackbarFlow.test {
            awaitItem() shouldBe errorSnackbar
        }
    }

    @Test
    fun `When clicked activate Then show correct loading state`() = runTest {
        coEvery { activateCardUseCase.invoke() } returns Unit.right()
        every { resourceResolver.getString(R.string.activation__success_snackbar_title) } returns "Success"
        createViewModel()

        viewModel.loadingState.test {
            awaitItem() shouldBe false
            viewModel.onAction(Action.ActivateClick)
            awaitItem() shouldBe true
            awaitItem() shouldBe false
        }
    }
}
