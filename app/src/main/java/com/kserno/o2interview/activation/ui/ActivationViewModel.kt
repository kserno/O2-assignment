package com.kserno.o2interview.activation.ui

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.kserno.o2interview.R
import com.kserno.o2interview.activation.domain.ActivateCardUseCase
import com.kserno.o2interview.core.exception.ExceptionUiMapper
import com.kserno.o2interview.core.invoke
import com.kserno.o2interview.core.viewmodel.BaseViewModel
import com.kserno.o2interview.ui.ButtonState
import com.kserno.o2interview.ui.FormattedString
import com.kserno.o2interview.ui.SnackbarEvent
import com.kserno.o2interview.ui.asResourceString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivationViewModel @Inject constructor(
    private val activateCard: ActivateCardUseCase,
    private val exceptionUiMapper: ExceptionUiMapper,
) : BaseViewModel<State, Event, Action>() {
    override val title: FormattedString = R.string.activation__activate_button_title.asResourceString()
    override val initialState: State
        get() = State(
            activationButton = ButtonState(
                text = R.string.activation__activate_button_title.asResourceString(),
                onClick = { onAction(Action.ActivateClick) },
            ),
        )

    override fun handleAction(action: Action) {
        when (action) {
            Action.ActivateClick -> activateClick()
        }
    }

    private fun activateClick() {
        activate()
    }

    private fun activate() {
        showLoading()
        viewModelScope.launch {
            activateCard()
                .fold(
                    ifLeft = ::onActivateFailed,
                    ifRight = { onActivateSuccess() },
                )
        }
    }

    private fun onActivateSuccess() {
        hideLoading()
        emitSnackbar(
            SnackbarEvent.SuccessSnackbar(
                message = R.string.activation__success_snackbar_title.asResourceString(),
            ),
        )
    }

    private fun onActivateFailed(throwable: Throwable) {
        hideLoading()
        emitSnackbar(exceptionUiMapper.map(throwable, retry = ::activate))
    }
}

@Immutable
data class State(
    val activationButton: ButtonState,
)

sealed interface Event

sealed interface Action {
    data object ActivateClick : Action
}
