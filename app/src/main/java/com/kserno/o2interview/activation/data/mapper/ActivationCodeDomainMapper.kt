package com.kserno.o2interview.activation.data.mapper

import com.kserno.o2interview.activation.data.json.JsonActivationCode
import com.kserno.o2interview.activation.domain.ActivationCode
import com.kserno.o2interview.core.exception.MappingException
import javax.inject.Inject

class ActivationCodeDomainMapper @Inject constructor() {

    fun map(jsonActivationCode: JsonActivationCode): ActivationCode {
        return kotlin.runCatching {
            ActivationCode(android = jsonActivationCode.android.toInt())
        }.getOrElse { throw MappingException(it) }
    }
}
