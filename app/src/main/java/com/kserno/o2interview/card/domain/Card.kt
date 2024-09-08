package com.kserno.o2interview.card.domain

sealed interface Card {
    data object NotScratchedCard : Card
    data class ScratchedCard(val code: String) : Card
    data class ActivatedCard(val code: String) : Card
}
