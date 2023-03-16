package com.tsato.pages.auth

import com.tsato.components.auth.authInputComponent
import com.tsato.data.dto.LoginRequest
import com.tsato.login
import com.tsato.util.detectBrowser
import csstype.*
import emotion.react.css
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.VFC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import react.router.dom.Link

private val scope = MainScope()

val loginPage = VFC {
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
            +"Login"
        }
        div {
            authInputComponent {
                onSubmit = { email, password ->
                    scope.launch {
                        login(
                            LoginRequest(
                                email = email,
                                password = password,
                                role = "User",
                                is2FAOn = false,
                                media = detectBrowser()
                            )
                        )
                    }
                }
            }
            div {
                +"Not a member?  "

                Link {
                    to = "/register"

                    +"Register"
                }
            }
        }
    }
}