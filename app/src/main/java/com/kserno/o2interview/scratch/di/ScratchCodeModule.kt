package com.kserno.o2interview.scratch.di

import com.kserno.o2interview.scratch.data.ScratchCodeDataModule
import com.kserno.o2interview.scratch.domain.di.ScratchCodeDomainModule
import com.kserno.o2interview.scratch.ui.di.ScratchCodeUiModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(includes = [ScratchCodeDataModule::class, ScratchCodeDomainModule::class, ScratchCodeUiModule::class])
class ScratchCodeModule
