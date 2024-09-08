package com.kserno.o2interview.activation.di

import com.kserno.o2interview.activation.data.di.ActivationDataModule
import com.kserno.o2interview.activation.domain.di.ActivationDomainModule
import com.kserno.o2interview.activation.ui.di.ActivationUiModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [ActivationDataModule::class, ActivationDomainModule::class, ActivationUiModule::class])
@InstallIn(SingletonComponent::class)
class ActivationModule
