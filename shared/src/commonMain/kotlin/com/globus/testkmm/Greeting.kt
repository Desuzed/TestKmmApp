package com.globus.testkmm

import com.globus.testkmm.data.network.HttpClientFactory
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable

@Serializable
data class Hello(val string: String, val lang: String)

class Greeting(private val httpClientFactory: HttpClientFactory) {

    suspend fun greeting(): String {
        return "${Platform().platform}! \n${getHello()}"
    }

    private suspend fun getHello(): List<Hello> {
        val response: HttpResponse =
            httpClientFactory.get(testUrl)
        return response.body()
    }

    companion object {
        private const val testUrl =
            "https://gitcdn.link/cdn/KaterinaPetrova/greeting/7d47a42fc8d28820387ac7f4aaf36d69e434adc1/greetings.json"
    }
}