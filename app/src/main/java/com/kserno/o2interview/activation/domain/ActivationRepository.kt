package com.kserno.o2interview.activation.domain

interface ActivationRepository {
    suspend fun activateCode(code: String): ActivationCode
}

@JvmInline
value class ActivationCode(val android: Int)
