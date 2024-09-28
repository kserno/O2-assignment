package com.kserno.o2interview.core.di

import com.kserno.o2interview.core.ResourceResolver
import com.kserno.o2interview.core.ResourceResolverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [CoreUiModule::class])
@InstallIn(SingletonComponent::class)
interface CoreModule
