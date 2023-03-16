package com.tsato.plugins

import com.tsato.di.mainModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

internal fun Application.configureDependencyInjection() {
    install(Koin) {
        SLF4JLogger()
        modules(mainModule)
    }
}