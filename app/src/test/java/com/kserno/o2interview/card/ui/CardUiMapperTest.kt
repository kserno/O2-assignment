package com.kserno.o2interview.card.ui

import com.kserno.o2interview.R
import com.kserno.o2interview.card.domain.Card
import com.kserno.o2interview.ui.CardState
import com.kserno.o2interview.ui.asPlainString
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CardUiMapperTest {

    private val mapper = CardUiMapper()

    @Test
    fun `Given not scratched Then map correctly`() = runTest {
        mapper.map(Card.NotScratchedCard) shouldBe CardState(
            backgroundColor = R.color.gray,
            text = null,
        )
    }

    @Test
    fun `Given scratched Then map correctly`() {
        mapper.map(Card.ScratchedCard("code")) shouldBe CardState(
            backgroundColor = R.color.yellow,
            text = "code".asPlainString(),
        )
    }

    @Test
    fun `Given activated Then map correctly`() {
        mapper.map(Card.ActivatedCard("code")) shouldBe CardState(
            backgroundColor = R.color.success,
            text = "code".asPlainString(),
        )
    }
}
