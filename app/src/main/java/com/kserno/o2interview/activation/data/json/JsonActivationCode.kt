package com.kserno.o2interview.activation.data.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JsonActivationCode(
    @SerialName("android") val android: String,
)
