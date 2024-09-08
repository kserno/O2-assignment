package com.kserno.o2interview.activation.data

import com.kserno.o2interview.activation.data.mapper.ActivationCodeDomainMapper
import com.kserno.o2interview.activation.data.service.ActivationService
import com.kserno.o2interview.activation.domain.ActivationCode
import com.kserno.o2interview.activation.domain.ActivationRepository
import javax.inject.Inject

class ActivationRepositoryImpl @Inject constructor(
    private val activationService: ActivationService,
    private val activationCodeDomainMapper: ActivationCodeDomainMapper,
) : ActivationRepository {
    override suspend fun activateCode(code: String): ActivationCode {
        return activationService.getActivationCode(code).let(activationCodeDomainMapper::map)
    }
}
