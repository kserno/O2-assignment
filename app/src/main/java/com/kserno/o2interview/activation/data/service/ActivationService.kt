package com.kserno.o2interview.activation.data.service

import com.kserno.o2interview.activation.data.json.JsonActivationCode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class ActivationService @Inject constructor(private val httpClient: HttpClient) {

    suspend fun getActivationCode(code: String): JsonActivationCode =
        httpClient.get("/version") {
            parameter("code", code)
        }.body()
}
