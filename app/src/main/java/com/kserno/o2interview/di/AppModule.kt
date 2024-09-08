package com.kserno.o2interview.di

import com.kserno.o2interview.activation.di.ActivationModule
import com.kserno.o2interview.card.di.CardModule
import com.kserno.o2interview.core.di.CoreModule
import com.kserno.o2interview.scratch.di.ScratchCodeModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        ActivationModule::class,
        CardModule::class,
        ScratchCodeModule::class,
        CoreModule::class,
    ],
)
class AppModule
