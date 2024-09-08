package com.kserno.o2interview.core

import android.content.Context
import androidx.annotation.StringRes
import com.kserno.o2interview.ui.FormattedString
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ResourceResolver {
    fun getString(@StringRes resId: Int): String
    fun getString(formattedString: FormattedString): String
}

class ResourceResolverImpl @Inject constructor(@ApplicationContext private val appContext: Context) : ResourceResolver {

    override fun getString(@StringRes resId: Int): String {
        return appContext.getString(resId)
    }

    override fun getString(formattedString: FormattedString): String {
        return when (formattedString) {
            is FormattedString.PlainString -> formattedString.text
            is FormattedString.ResourceString -> appContext.getString(formattedString.id)
        }
    }
}
