package com.kserno.o2interview.core.viewmodel

import com.kserno.o2interview.ui.SnackbarEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SnackbarHandlerDelegate : SnackbarHandler {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _snackbarFlow = Channel<SnackbarEvent>(Channel.CONFLATED)
    override val snackbarFlow: Flow<SnackbarEvent> = _snackbarFlow.receiveAsFlow()

    override fun emitSnackbar(snackbarEvent: SnackbarEvent) {
        scope.launch {
            _snackbarFlow.send(snackbarEvent)
        }
    }

    override fun clearSnackbarHandlerScope() {
        scope.cancel()
    }
}
