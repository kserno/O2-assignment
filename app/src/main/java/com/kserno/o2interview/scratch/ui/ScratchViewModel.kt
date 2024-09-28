package com.kserno.o2interview.scratch.ui

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.kserno.o2interview.R
import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.card.domain.ObserveCardUseCase
import com.kserno.o2interview.card.ui.CardUiMapper
import com.kserno.o2interview.core.exception.ExceptionUiMapper
import com.kserno.o2interview.core.invoke
import com.kserno.o2interview.core.viewmodel.BaseViewModel
import com.kserno.o2interview.core.viewmodel.launch
import com.kserno.o2interview.scratch.domain.ScratchCardUseCase
import com.kserno.o2interview.ui.ButtonState
import com.kserno.o2interview.ui.CardState
import com.kserno.o2interview.ui.FormattedString
import com.kserno.o2interview.ui.SnackbarEvent
import com.kserno.o2interview.ui.asResourceString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScratchViewModel @Inject constructor(
    private val observeCard: ObserveCardUseCase,
    private val scratchCard: ScratchCardUseCase,
    private val cardUiMapper: CardUiMapper,
    private val exceptionUiMapper: ExceptionUiMapper,
) : BaseViewModel<State, Event, Action>() {
    override val title: FormattedString = R.string.scratch__toolbar_title.asResourceString()
    override val initialState: State get() = State.Loading

    init {
        observeCard()
            .onEach {
                emitState(
                    State.Content(
                        cardState = cardUiMapper.map(it),
                        scratchButton = scratchButton(enabled = it !is Card.ScratchedCard),
                    ),
                )
            }
            .launch()
    }

    private fun scratchButton(enabled: Boolean) = ButtonState(
        text = R.string.scratch__scratch_button_text.asResourceString(),
        enabled = enabled,
        onClick = { onAction(Action.ScratchClick) },
    )

    override fun handleAction(action: Action) {
        when (action) {
            Action.ScratchClick -> scratchClick()
        }
    }

    private fun scratchClick() {
        scratch()
    }

    private fun scratch() {
        showLoading()
        viewModelScope.launch {
            scratchCard()
                .fold(
                    ifLeft = ::onScratchFailed,
                    ifRight = { onScratchSuccess() },
                )
        }
    }

    private fun onScratchFailed(throwable: Throwable) {
        hideLoading()
        emitSnackbar(exceptionUiMapper.map(throwable, ::scratch))
    }

    private fun onScratchSuccess() {
        hideLoading()
        emitSnackbar(SnackbarEvent.InfoSnackbar(R.string.scratch__scratched_success.asResourceString()))
    }
}

@Immutable
sealed interface State {
    data class Content(
        val cardState: CardState,
        val scratchButton: ButtonState,
    ) : State
    data object Loading : State
}

sealed interface Event

sealed interface Action {
    data object ScratchClick : Action
}
