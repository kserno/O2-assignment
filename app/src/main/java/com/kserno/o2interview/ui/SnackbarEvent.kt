package com.kserno.o2interview.ui

// Ugh cannot use FormattedString here because compose api isn't compatible
sealed class SnackbarEvent(open val message: String) {
    data class InfoSnackbar(override val message: String) : SnackbarEvent(message)
    data class ErrorSnackbar(override val message: String, val retryAction: () -> Unit) : SnackbarEvent(message)
    data class SuccessSnackbar(override val message: String) : SnackbarEvent(message)
}
