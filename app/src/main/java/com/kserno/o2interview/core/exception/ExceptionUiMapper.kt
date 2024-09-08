package com.kserno.o2interview.core.exception

import android.util.Log
import com.kserno.o2interview.R
import com.kserno.o2interview.core.ResourceResolver
import com.kserno.o2interview.core.di.ExceptionUiMapping
import com.kserno.o2interview.ui.SnackbarEvent
import com.kserno.o2interview.ui.asResourceString
import javax.inject.Inject
import javax.inject.Provider

class ExceptionUiMapper @Inject constructor(
    private val mappings: MutableMap<Class<out Throwable>, Provider<ExceptionUiMapping>>,
    private val resourceResolver: ResourceResolver,
) {

    fun map(throwable: Throwable, retry: () -> Unit = {}): SnackbarEvent.ErrorSnackbar {
        var message = mappings[throwable::class.java]?.get()?.map(throwable)

        if (message == null) {
            message = R.string.error__unknown_error.asResourceString()
            Log.d(ExceptionUiMapper::class.simpleName, "Unknown unmapped exception: $throwable")
        }
        return SnackbarEvent.ErrorSnackbar(message = resourceResolver.getString(message), retryAction = retry)
    }
}
