package com.kserno.o2interview.core.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kserno.o2interview.ui.FormattedString
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event, Action> :
    ViewModel(),
    LoadingHandler by LoadingHandlerDelegate(),
    SnackbarHandler by SnackbarHandlerDelegate() {

    private val _event = Channel<Event>(capacity = Channel.CONFLATED)
    val event: Flow<Event> = _event.receiveAsFlow()

    abstract val title: FormattedString

    private val _state by lazy {
        MutableStateFlow(initialState)
    }
    val state: StateFlow<State> = _state

    protected abstract val initialState: State

    fun onAction(action: Action) {
        Log.d(this::class.simpleName, "handleAction: $action")
        handleAction(action)
    }

    fun emitState(state: State) {
        Log.d(this::class.simpleName, "emitState: $state")
        _state.value = state
    }

    protected fun emitEvent(event: Event) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    override fun onCleared() {
        clearSnackbarHandlerScope()
        clearLoadingHandlerScope()
        super.onCleared()
    }

    abstract fun handleAction(action: Action)
}

context(ViewModel)
fun <T> Flow<T>.launch() = launchIn(viewModelScope)
