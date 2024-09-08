package com.kserno.o2interview.main.ui

import androidx.compose.runtime.Immutable
import com.kserno.o2interview.R
import com.kserno.o2interview.card.domain.ObserveCardUseCase
import com.kserno.o2interview.card.ui.CardUiMapper
import com.kserno.o2interview.core.invoke
import com.kserno.o2interview.core.viewmodel.BaseViewModel
import com.kserno.o2interview.core.viewmodel.launch
import com.kserno.o2interview.ui.ButtonState
import com.kserno.o2interview.ui.CardState
import com.kserno.o2interview.ui.FormattedString
import com.kserno.o2interview.ui.asResourceString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeCard: ObserveCardUseCase,
    private val cardUiMapper: CardUiMapper,
) : BaseViewModel<State, Event, Action>() {
    override val title: FormattedString = R.string.main__toolbar_title.asResourceString()
    override val initialState: State
        get() = State.Loading

    init {
        observeCard()
            .onEach {
                emitState(
                    State.Content(
                        cardState = cardUiMapper.map(it),
                        activationButtonState = activationButton(),
                        scratchButtonState = scratchButton(),
                    ),
                )
            }
            .launch()
    }

    override fun handleAction(action: Action) {
        when (action) {
            Action.ActivationClick -> activationClick()
            Action.ScratchClick -> scratchClick()
        }
    }

    private fun activationClick() {
        emitEvent(Event.GoToActivation)
    }

    private fun scratchClick() {
        emitEvent(Event.GoToScratch)
    }

    private fun scratchButton() = ButtonState(
        text = R.string.main__scratch_button_title.asResourceString(),
        onClick = { onAction(Action.ScratchClick) },
    )

    private fun activationButton() = ButtonState(
        text = R.string.main__activation_button_title.asResourceString(),
        onClick = { onAction(Action.ActivationClick) },
    )
}

@Immutable
sealed interface State {
    data object Loading : State
    data class Content(
        val cardState: CardState,
        val activationButtonState: ButtonState,
        val scratchButtonState: ButtonState,
    ) : State
}

sealed interface Action {
    data object ScratchClick : Action
    data object ActivationClick : Action
}

sealed interface Event {
    data object GoToScratch : Event
    data object GoToActivation : Event
}
