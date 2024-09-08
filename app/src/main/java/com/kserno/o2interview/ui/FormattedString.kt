package com.kserno.o2interview.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface FormattedString {
    data class ResourceString(@StringRes val id: Int) : FormattedString
    data class PlainString(val text: String) : FormattedString
}

fun String.asPlainString() = FormattedString.PlainString(this)
fun @receiver:StringRes Int.asResourceString() = FormattedString.ResourceString(this)

@Composable
fun FormattedString.asString() = when (this) {
    is FormattedString.ResourceString -> stringResource(id)
    is FormattedString.PlainString -> text
}
