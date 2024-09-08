package com.kserno.o2interview.core.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoadingHandlerDelegate : LoadingHandler {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _loadingState by lazy {
        MutableStateFlow(false)
    }

    override val loadingState: StateFlow<Boolean>
        get() = _loadingState

    override fun showLoading() {
        scope.launch {
            _loadingState.value = true
        }
    }

    override fun hideLoading() {
        scope.launch {
            _loadingState.value = false
        }
    }

    override fun clearLoadingHandlerScope() {
        scope.cancel()
    }
}
