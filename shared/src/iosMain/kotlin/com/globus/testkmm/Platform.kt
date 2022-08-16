package com.globus.testkmm

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.engine.ios.*
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(Darwin) {
    config(this)

    engine {
        configureRequest {

        }
    }
}

actual fun initLogger() {
    Napier.base(DebugAntilog())
}