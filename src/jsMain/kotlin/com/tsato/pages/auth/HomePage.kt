package com.tsato.pages.auth

import com.tsato.components.footerComponent
import com.tsato.components.headerComponent
import csstype.*
import emotion.react.css
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.router.useNavigate
import web.storage.localStorage

private val scope = MainScope()

val homePage = FC<Props> {
    val navigate = useNavigate()

    var isDrawerOpen: Boolean by useState(true)

    useEffectOnce {
        scope.launch {
            isDrawerOpen = (localStorage.getItem("isDrawerOpen") ?: "true").toBooleanStrict()
        }
    }
    useEffect(isDrawerOpen) {
        scope.launch {
            localStorage.setItem("isDrawerOpen", isDrawerOpen.toString())
        }
    }

    headerComponent {}
    div {
        css {
            width = 100.pct
            minHeight = 100.vh
            display = Display.flex
            flexDirection = FlexDirection.row
        }
        div {
            css {
                width = if (isDrawerOpen) 300.px else 60.px
                height = 100.pct
                flexDirection = FlexDirection.column
                backgroundColor = Color("blue")
            }
            button {
                css {
                    width = 60.px
                    height = 50.px
                }
                onClick = {
                    isDrawerOpen = !isDrawerOpen
                }
                +if (isDrawerOpen) "<-" else "->"
            }
            ul {
                if (isDrawerOpen) {
                    li {
                        +"Menu items"
                    }
                    li {
                        +"About Akinahi"
                    }
                } else {
                    li {
                        +"M"
                    }
                    li {
                        +"A"
                    }
                }
            }
        }
        div {
            css {
                width = 100.pct
                backgroundColor = Color("green")
            }
            +"Something"
            button {
                onClick = {
                    navigate("/item/create")
                }
                +"Create new item"
            }
        }
    }
    footerComponent {}
}