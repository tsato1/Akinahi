package com.tsato.components.auth

import com.tsato.util.isEmailValid
import com.tsato.util.isPasswordValid
import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEventHandler
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useState
import web.html.HTMLFormElement
import web.html.HTMLInputElement
import web.html.InputType

external interface AuthInputProps : Props {
    var onSubmit: (String, String) -> Unit
}

val authInputComponent = FC<AuthInputProps> { props ->
    val (email, setEmail) = useState("")
    val (password, setPassword) = useState("")
    val (message, setMessage) = useState("")

    val submitHandler: FormEventHandler<HTMLFormElement> = {
        if (!isEmailValid(email) || !isPasswordValid(password)) {
            setMessage("Email address or password is not in the valid format.")
        }
        else {
            setEmail("")
            setPassword("")
            props.onSubmit(email, password)
        }
        it.preventDefault()
    }

    val emailChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setEmail(it.target.value)
    }
    val passwordChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setPassword(it.target.value)
    }

    div {
        css {
            minWidth = 400.px
            width = 40.pct
            display = Display.flex
            flexDirection = FlexDirection.column
            padding = Padding(40.px, 20.px)
            border = Border(3.px, LineStyle.solid, Color("#000"))
            borderRadius = 5.px
        }
        form {
            onSubmit = submitHandler

            label {
                +"Email:"
            }
            br {}
            input {
                css {
                    width = 100.pct
                    padding = Padding(10.px, 10.px)
                    border = Border(1.px, LineStyle.solid, Color("#ccc"))
                    borderRadius = 5.px
                    boxSizing = BoxSizing.borderBox
                }
                type = InputType.email
                onChange = emailChangeHandler
                value = email
            }
            br {}
            br {}
            label {
                +"Password:"
            }
            br {}
            input {
                css {
                    width = 100.pct
                    padding = Padding(10.px, 10.px)
                    border = Border(1.px, LineStyle.solid, Color("#ccc"))
                    borderRadius = 5.px
                    boxSizing = BoxSizing.borderBox
                }
                type = InputType.password
                onChange = passwordChangeHandler
                value = password
            }
            br {}
            br {}
            div {
                css {
                    color = Color("red")
                }
                +message
            }
            input {
                css {
                    float = Float.right
                }
                type = InputType.submit
                value = "Submit"
            }
        }
    }
}