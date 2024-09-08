package com.kserno.o2interview.core.viewmodel

import com.kserno.o2interview.ui.SnackbarEvent
import kotlinx.coroutines.flow.Flow

interface SnackbarHandler {
    val snackbarFlow: Flow<SnackbarEvent>

    fun emitSnackbar(snackbarEvent: SnackbarEvent)
    fun clearSnackbarHandlerScope()
}
