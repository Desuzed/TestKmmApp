package com.globus.testkmm.data.network

import com.globus.testkmm.httpClient
import com.globus.testkmm.initLogger
import io.github.aakira.napier.Napier
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object HttpClientFactory {
    private const val TAG = "HTTP client"

    val httpClient = httpClient {
        install(Logging) {
            level = LogLevel.HEADERS
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(tag = TAG, message = message)
                }
            }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }.also {
        initLogger()
    }

    suspend inline fun <reified T> get(url: String): T = httpClient.get(url).body()

}