package com.kserno.o2interview.activation.data

import com.kserno.o2interview.activation.data.json.JsonActivationCode
import com.kserno.o2interview.activation.data.mapper.ActivationCodeDomainMapper
import com.kserno.o2interview.activation.data.service.ActivationService
import com.kserno.o2interview.activation.domain.ActivationCode
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ActivationRepositoryTest {

    private val activationService: ActivationService = mockk()
    private val activationCodeDomainMapper: ActivationCodeDomainMapper = mockk()

    private val activationRepository = ActivationRepositoryImpl(activationService, activationCodeDomainMapper)

    @Test
    fun `Should return correctly mapped from data source`() = runTest {
        val json = JsonActivationCode("code")
        val mapped = ActivationCode(0)
        coEvery { activationService.getActivationCode(any()) } returns json
        every { activationCodeDomainMapper.map(json) } returns mapped

        activationRepository.activateCode("code") shouldBe mapped
    }
}
