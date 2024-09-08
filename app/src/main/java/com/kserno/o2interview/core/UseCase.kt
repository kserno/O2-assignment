package com.kserno.o2interview.core

interface UseCase<Input, Output> {
    operator fun invoke(input: Input): Output
}

interface SuspendUseCase<Input, Output> {
    suspend operator fun invoke(input: Input): Output
}

inline operator fun <reified T> UseCase<Unit, T>.invoke(): T = this(Unit)
suspend inline operator fun <reified T> SuspendUseCase<Unit, T>.invoke(): T = this(Unit)
