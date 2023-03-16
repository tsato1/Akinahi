package com.tsato.components

import csstype.*
import emotion.react.css
import react.FC
import react.VFC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.router.useNavigate

val headerComponent = VFC {
    val navigate = useNavigate()

    div {
        css {
            width = 100.pct
            height = 60.px
            display = Display.flex
            flexDirection = FlexDirection.row
            justifyContent = JustifyContent.spaceBetween
            alignContent = AlignContent.center
            backgroundColor = Color("red")
        }
        h1 {
            +"Akinahi"
        }
        button {
            css {
                width = 60.px
            }
            onClick = {
                navigate("/login")
            }
            +"Login"
        }
    }
}