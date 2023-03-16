package com.tsato

import com.tsato.pages.auth.endOfRegistrationPage
import com.tsato.pages.auth.homePage
import com.tsato.pages.auth.loginPage
import com.tsato.pages.auth.registerPage
import com.tsato.pages.item.createNewItemPage
import react.VFC
import react.create
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.router.PathRoute
import react.router.Routes
import react.router.dom.BrowserRouter

val App = VFC {
    BrowserRouter {
        div {
            Routes {
                PathRoute {
                    path = "/"
                    element = homePage.create()
                }

                /* Auth */
                PathRoute {
                    path = "/login"
                    element = loginPage.create()
                }
                PathRoute {
                    path = "/register"
                    element = registerPage.create()
                }
                PathRoute {
                    path = "/endOfRegistration"
                    element = endOfRegistrationPage.create()
                }

                /* Item */
                PathRoute {
                    path = "/item/create"
                    element = createNewItemPage.create()
                }
            }
        }
    }
}