package com.kserno.o2interview.card.di

import com.kserno.o2interview.card.data.di.CardDataModule
import com.kserno.o2interview.card.domain.di.CardDomainModule
import com.kserno.o2interview.card.ui.di.CardUiModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [CardDataModule::class, CardDomainModule::class, CardUiModule::class])
@InstallIn(SingletonComponent::class)
class CardModule
