package com.tsato.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.tsato.security.token.TokenConfig
import io.ktor.server.application.*

fun Application.configureSecurity(
    accessTokenConfig: TokenConfig
) {
    authentication {
        jwt("access") {
            realm = "Tsato App"
            verifier(
                JWT
                    .require(Algorithm.HMAC256(accessTokenConfig.secret))
                    .withAudience(accessTokenConfig.audience)
                    .withIssuer(accessTokenConfig.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(accessTokenConfig.audience)) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
