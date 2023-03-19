package com.tsato.components

import csstype.*
import emotion.css.keyframes
import emotion.react.css
import pathToCloseIcon
import react.FC
import react.Props
import react.VFC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.img
import web.dom.document
import web.html.HTMLDivElement

external interface ModalProps: Props {
    var headerTitle: String
    var content: VFC
    var leftButtonLabel: String?
    var onLeftButtonClick: (() -> Unit)?
    var rightButtonLabel: String?
    var onRightButtonClick: (() -> Unit)?
}

val modalComponent = FC<ModalProps> { props ->
    div {
        css {
            width = 100.pct
            height = 100.pct
            position = Position.fixed
            top = 0.px
            left = 0.px
            backgroundColor = rgba(0, 0, 0, 0.5)
            display = None.none
        }
        id = "modal-container"
        div {
            css {
                width = 50.pct
                margin = 20.pct
                padding = 20.px
                backgroundColor = Color("white")
                position = Position.relative
                borderRadius = 5.px
                animationDuration = 1.s
                keyframes {
                    from { opacity(0) }
                    to { opacity(1) }
                }
            }
            id = "modal"
            img {
                css {
                    width = 24.px
                    height = 24.px
                    margin = 8.px
                    backgroundColor = Color("white")
                    position = Position.absolute
                    top = 0.px
                    right = 0.px
                    borderRadius = 5.px
                    hover {
                        cursor = Cursor.pointer
                    }
                }
                id = "close-modal-btn"
                src = pathToCloseIcon
                onClick = {
                    val modalContainer = document.getElementById("modal-container") as HTMLDivElement
                    modalContainer.style.display = None.none.toString()
                }
            }
            h2 {
                +props.headerTitle
            }
            div {
                props.content {}
            }
            if (props.leftButtonLabel != null || props.rightButtonLabel != null) {
                div {
                    css {
                        paddingRight = 30.px
                        paddingLeft = 30.px
                        display = Display.flex
                        flexDirection = FlexDirection.row
                        justifyContent = JustifyContent.spaceBetween
                    }
                    props.leftButtonLabel?.let {
                        button {
                            css {
                                width = 120.px
                                padding = 10.px
                            }
                            onClick = {
                                props.onLeftButtonClick
                            }
                            +props.leftButtonLabel
                        }
                    }
                    props.rightButtonLabel?.let {
                        button {
                            css {
                                width = 120.px
                                padding = 10.px
                            }
                            onClick = {
                                props.onRightButtonClick
                            }
                            +props.rightButtonLabel
                        }
                    }
                }
            }
        }
    }
}