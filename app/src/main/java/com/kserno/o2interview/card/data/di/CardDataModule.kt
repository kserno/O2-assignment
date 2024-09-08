package com.kserno.o2interview.card.data.di

import com.kserno.o2interview.card.data.CardRepositoryImpl
import com.kserno.o2interview.card.domain.CardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CardDataModule {
    @Binds
    fun bindCardRepository(impl: CardRepositoryImpl): CardRepository
}
