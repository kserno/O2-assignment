package com.kserno.o2interview.scratch.data

import com.kserno.o2interview.scratch.domain.ScratchCodeGenerator
import java.util.UUID
import javax.inject.Inject

class ScratchCodeGeneratorImpl @Inject constructor() : ScratchCodeGenerator {
    override fun generateCode(): String {
        return UUID.randomUUID().toString()
    }
}
