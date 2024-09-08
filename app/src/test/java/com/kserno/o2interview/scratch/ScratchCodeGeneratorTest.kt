package com.kserno.o2interview.scratch

import com.kserno.o2interview.scratch.data.ScratchCodeGeneratorImpl
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Test
import java.util.UUID

class ScratchCodeGeneratorTest {

    private val generator = ScratchCodeGeneratorImpl()

    @Test
    fun `Should return UUID as string`() {
        val code = "350e8400-e29b-41d4-a716-446655440000"
        val uuid = UUID.fromString(code)
        mockkStatic(UUID::class)
        every { UUID.randomUUID() } returns uuid

        generator.generateCode() shouldBe code
    }
}
