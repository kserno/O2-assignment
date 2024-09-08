package com.kserno.o2interview.activation.ui.di

import com.kserno.o2interview.R
import com.kserno.o2interview.activation.di.ExceptionMapping
import com.kserno.o2interview.activation.domain.CardActivationCodeTooLowException
import com.kserno.o2interview.activation.domain.CardAlreadyActivatedException
import com.kserno.o2interview.activation.domain.CardNotScratchedException
import com.kserno.o2interview.core.di.ExceptionUiMapping
import com.kserno.o2interview.ui.asResourceString
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
class ActivationUiModule {

    @Provides
    @IntoMap
    @ExceptionMapping(CardNotScratchedException::class)
    fun provideCardNotScratchedExceptionMapping(): ExceptionUiMapping {
        return ExceptionUiMapping { R.string.error__card_not_scratched.asResourceString() }
    }

    @Provides
    @IntoMap
    @ExceptionMapping(CardAlreadyActivatedException::class)
    fun provideCardActivatedExceptionMapping(): ExceptionUiMapping {
        return ExceptionUiMapping { R.string.error__card_already_activated.asResourceString() }
    }

    @Provides
    @IntoMap
    @ExceptionMapping(CardActivationCodeTooLowException::class)
    fun provideCardActivationCodeTooLowExceptionMapping(): ExceptionUiMapping {
        return ExceptionUiMapping { R.string.error__card_activation_too_low.asResourceString() }
    }
}
