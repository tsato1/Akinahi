package com.tsato

import com.tsato.plugins.*
import com.tsato.plugins.configureDependencyInjection
import com.tsato.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    val env = applicationEngineEnvironment {
        module {
            backend()
            frontend()
        }
        connector {
            host = "0.0.0.0"
            port = 8080
        }
        connector {
            host = "0.0.0.0"
            port = 3000
        }
    }
    embeddedServer(Netty, env).start(true)
}

fun Application.backend() {
    configureDependencyInjection()
    configureSerialization()
    configureMonitoring()

    val accessTokenConfig = TokenConfig(
        issuer = "https://auth.tsato.com",
        audience = "jwt-audience-access",
        expiresIn = 600000,
        secret = System.getenv("ACCESS_TOKEN_SECRET")
    )
    configureSecurity(accessTokenConfig)
    configureBackendRouting()
}

fun Application.frontend() {
    configureFrontendRouting()
}