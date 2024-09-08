package com.kserno.o2interview.core.di

import com.kserno.o2interview.R
import com.kserno.o2interview.activation.di.ExceptionMapping
import com.kserno.o2interview.core.exception.MappingException
import com.kserno.o2interview.ui.asResourceString
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
class CoreUiModule {

    @Provides
    @IntoMap
    @ExceptionMapping(MappingException::class)
    fun providesMappingExceptionMapping(): ExceptionUiMapping {
        return ExceptionUiMapping { R.string.error__mapping_exception.asResourceString() }
    }
}
