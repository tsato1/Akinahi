package com.tsato.pages.auth

import com.tsato.components.auth.authInputComponent
import com.tsato.data.dto.RegisterRequest
import com.tsato.register
import com.tsato.util.detectBrowser
import csstype.*
import emotion.react.css
import io.ktor.http.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.VFC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import react.router.dom.Link
import react.router.useNavigate

private val scope = MainScope()

val registerPage = VFC {
    val navigate = useNavigate()

    var errorMessage: String? = null

    div {
        css {
            display = Display.flex
            justifyContent = JustifyContent.center
            padding = Padding(40.px, 20.px)
        }

        h3 {
            css {
                position = Position.absolute
                margin = Margin((-20).px, 0.px)
                border = Border(3.px, LineStyle.solid, Color("000"))
                borderRadius = 5.px
                backgroundColor = Color("#ccc")
                padding = 10.px
            }
            +"Register"
        }
        div {
            authInputComponent {
                onSubmit = { email, password ->
                    scope.launch {
                        val response = register(
                            RegisterRequest(
                                email = email,
                                password = password,
                                role = "Regular",
                                is2FAOn = false,
                                media = detectBrowser()
                            )
                        )

                        when (response.status) {
                            HttpStatusCode.OK -> {
                                navigate("/endOfRegistration")
                            }
                            HttpStatusCode.NotAcceptable -> {

                            }
                            HttpStatusCode.Conflict -> {
                                errorMessage = "There is already a user that has the same email address as the one that you specified."
                            }
                            else -> {
                                errorMessage = "Registration failed. Please contact the support. The error code was ${response.status}"
                            }
                        }
                    }
                }
            }
            div {
                +"Already a member?  "

                Link {
                    to = "/login"

                    +"Login"
                }
            }

            errorMessage?.let {
                div {
                    css {
                        width = 100.pct
                        height = 100.pct
                        position = Position.fixed
                        backgroundColor = rgba(0, 0, 0, 0.4)
                    }
                    div {
                        css {
                            width = 80.pct
                            backgroundColor = Color("#fefefe")
                        }
                        +it
                    }
                }
            }
        }
    }
}