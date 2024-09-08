package com.kserno.o2interview.activation.data

import com.kserno.o2interview.activation.data.json.JsonActivationCode
import com.kserno.o2interview.activation.data.mapper.ActivationCodeDomainMapper
import com.kserno.o2interview.core.exception.MappingException
import io.kotest.matchers.shouldBe
import org.junit.Test

class ActivationCodeDomainMapperTest {

    private val mapper = ActivationCodeDomainMapper()

    @Test
    fun `When mapping is int Then map correctly`() {
        val android = "3"
        val result = mapper.map(JsonActivationCode(android = android))

        result.android shouldBe android.toInt()
    }

    @Test(expected = MappingException::class)
    fun `When mapping is not correct Then throw mapping exception`() {
        mapper.map(JsonActivationCode(android = "android"))
    }
}
