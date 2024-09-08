package com.kserno.o2interview.core.di

import com.kserno.o2interview.ui.FormattedString

fun interface ExceptionUiMapping {
    fun map(throwable: Throwable): FormattedString
}
