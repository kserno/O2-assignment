package com.kserno.o2interview.core.viewmodel

import kotlinx.coroutines.flow.StateFlow

interface LoadingHandler {
    val loadingState: StateFlow<Boolean>

    fun showLoading()
    fun hideLoading()
    fun clearLoadingHandlerScope()
}
