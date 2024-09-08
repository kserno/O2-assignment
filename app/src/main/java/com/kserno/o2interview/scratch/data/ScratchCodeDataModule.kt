package com.kserno.o2interview.scratch.data

import com.kserno.o2interview.scratch.domain.ScratchCodeGenerator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface ScratchCodeDataModule {
    @Binds
    fun bindsCodeGenerator(impl: ScratchCodeGeneratorImpl): ScratchCodeGenerator
}
