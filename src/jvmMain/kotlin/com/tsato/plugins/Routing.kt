package com.tsato.plugins

import com.tsato.routes.itemRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.configureBackendRouting() {
    routing {
        route("api") {
            route("v1") {
                port(8080) {
                    authenticate("access") {
                        itemRoute()
                    }
                }
            }
        }
    }
}

fun Application.configureFrontendRouting() {
    routing {
        port(3000) {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("index.html")!!.readText(),
                    ContentType.Text.Html
                )
//                call.respondHtml {
//                    head {
//                        script(src = "/akinahi.js") {}
//                    }
//                    body {
//                        div {
//                            id = "root"
//                            + "Root"
//                        }
//                    }
//                }
            }
            static("/") {
                resources()
            }
        }
    }
}
