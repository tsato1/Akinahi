package com.tsato.components

import csstype.Color
import csstype.pct
import csstype.px
import emotion.react.css
import react.VFC
import react.dom.html.ReactHTML.div

val footerComponent = VFC {
    div {
        css {
            width = 100.pct
            height = 60.px
            backgroundColor = Color("yellow")
        }
        +"Footer!"
    }
}