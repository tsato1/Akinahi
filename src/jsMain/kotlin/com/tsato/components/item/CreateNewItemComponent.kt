package com.tsato.components.item

import com.tsato.data.Category
import com.tsato.util.isDescriptionValid
import com.tsato.util.isPriceValid
import com.tsato.util.isTitleValid
import csstype.*
import emotion.react.css
import pathToDefaultPhoto
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEventHandler
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.textarea
import react.dom.html.ReactHTML.th
import react.dom.html.ReactHTML.tr
import react.useState
import web.dom.document
import web.html.*

external interface CreateNewItemProps : Props {
    var onSubmit: (Category, String, String, String, List<ByteArray>) -> Unit
}

val createNewItemComponent = FC<CreateNewItemProps> { props ->
    val (category, setCategory) = useState<Category>()
    val (categoryErrorMessage, setCategoryErrorMessage) = useState("")
    val (title, setTitle) = useState("")
    val (titleErrorMessage, setTitleErrorMessage) = useState("")
    val (description, setDescription) = useState("")
    val (descriptionErrorMessage, setDescriptionErrorMessage) = useState("")
    val (price, setPrice) = useState("")
    val (priceErrorMessage, setPriceErrorMessage) = useState("")
    val (photoListErrorMessage, setPhotoListErrorMessage) = useState("")

    val submitHandler: FormEventHandler<HTMLFormElement> = {
        var hasError = false

        if (category == null || category is Category.NotSelected) {
            setCategoryErrorMessage("Category is a mandatory field")
            hasError = true
        }
        if (!isTitleValid(title)) {
            setTitleErrorMessage("Title is invalid")
            hasError = true
        }
        if (!isDescriptionValid(description)) {
            setDescriptionErrorMessage("Description is invalid")
            hasError = true
        }
        if (!isPriceValid(price)) {
            setPriceErrorMessage("Price is invalid")
            hasError = true
        }

        val items = document.querySelectorAll("ul li")
        val photos = mutableListOf<ByteArray>()
        items.forEach { item ->
            val photo = item.firstElementChild as HTMLImageElement
            if (photo.src != "http://0.0.0.0:3000$pathToDefaultPhoto") {
                photos.add(photo.src.encodeToByteArray())
            }
        }
        if (photos.isEmpty()) {
            setPhotoListErrorMessage("At least one photo is required.")
            hasError = true
        }

        if (!hasError) {
            props.onSubmit(
                category!!,
                title,
                description,
                price,
                photos
            )
            setTitle("")
            setDescription("")
            setPrice("")
        }
        it.preventDefault()
    }

    val titleChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setTitle(it.target.value)
    }
    val descriptionChangeHandler: ChangeEventHandler<HTMLTextAreaElement> = {
        setDescription(it.target.value)
    }
    val priceChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setPrice(it.target.value)
    }

    div {
        form {
            onSubmit = submitHandler

            table {
                css {
                    width = 100.pct
                    border = Border(1.px, LineStyle.solid, Color("gray"))
                    borderCollapse = BorderCollapse.collapse
                }
                tbody {
                    tr {
                        th {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            +"Category"
                            span {
                                css {
                                    color = Color("red")
                                }
                                +"*"
                            }
                        }
                        td {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            categorySelectionComponent {
                                categoryList = Category.getAllFoodCategories()
                                onCategoryChange = { index ->
                                    index?.let {
                                        setCategory(
                                            Category.getAllFoodCategories()[index]
                                        )
                                    }
                                }
                            }
                            p {
                                css {
                                    color = Color("red")
                                }
                                +categoryErrorMessage
                            }
                        }
                    }
                    tr {
                        th {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            +"Title"
                            span {
                                css {
                                    color = Color("red")
                                }
                                +"*"
                            }
                        }
                        td {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            input {
                                maxLength = 32
                                type = InputType.text
                                onChange = titleChangeHandler
                            }
                            p {
                                css {
                                    color = Color("red")
                                }
                                +titleErrorMessage
                            }
                        }
                    }
                    tr {
                        th {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            +"Description"
                            span {
                                css {
                                    color = Color("red")
                                }
                                +"*"
                            }
                        }
                        td {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            textarea {
                                css {
                                    maxWidth = 1000.px
                                }
                                cols = 40
                                rows = 7
                                maxLength = 9999
                                onChange = descriptionChangeHandler
                            }
                            p {
                                css {
                                    color = Color("red")
                                }
                                +descriptionErrorMessage
                            }
                        }
                    }
                    tr {
                        th {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            +"Price"
                            span {
                                css {
                                    color = Color("red")
                                }
                                +"*"
                            }
                        }
                        td {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            input {
                                maxLength = 8
                                size = 8
                                type = InputType.text
                                onChange = priceChangeHandler
                            }
                            p {
                                css {
                                    color = Color("red")
                                }
                                +priceErrorMessage
                            }
                        }
                    }
                    tr {
                        th {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            +"Photos"
                            span {
                                css {
                                    color = Color("red")
                                }
                                +"*"
                            }
                        }
                        td {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            photoListComponent {}
                            p {
                                css {
                                    color = Color("red")
                                }
                                +photoListErrorMessage
                            }
                        }
                    }
                }
            }
            div {
                css {
                    textAlign = TextAlign.center
                }
                input {
                    type = InputType.submit
                    value = "Submit"
                }
            }
        }
    }
}