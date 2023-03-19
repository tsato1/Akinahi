package com.tsato.components.item

import com.tsato.addItem
import com.tsato.components.modalComponent
import com.tsato.data.Category
import com.tsato.data.Status
import com.tsato.data.Status.Draft.getAllStatus
import com.tsato.data.Type
import com.tsato.data.model.ItemModel
import com.tsato.util.isDescriptionValid
import com.tsato.util.isPriceValid
import com.tsato.util.isTitleValid
import csstype.*
import emotion.react.css
import io.ktor.http.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import pathToDefaultPhoto
import react.VFC
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEventHandler
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.select
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

private val scope = MainScope()

val createNewItemComponent = VFC {
    val (category, setCategory) = useState<Category>(Category.Rice)
    val (categoryErrorMessage, setCategoryErrorMessage) = useState("")
    val (title, setTitle) = useState("")
    val (titleErrorMessage, setTitleErrorMessage) = useState("")
    val (description, setDescription) = useState("")
    val (descriptionErrorMessage, setDescriptionErrorMessage) = useState("")
    val (price, setPrice) = useState("")
    val (priceErrorMessage, setPriceErrorMessage) = useState("")
    val (tagList, setTagList) = useState<List<String>>(emptyList())
    val (photoList, setPhotoList) = useState<List<ByteArray>>(emptyList())
    val (photoListErrorMessage, setPhotoListErrorMessage) = useState("")

    val submitHandler: FormEventHandler<HTMLFormElement> = {
        var hasError = false

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
        } else {
            setPhotoList(photoList)
        }

        if (!hasError) {
            showModal()
            setCategoryErrorMessage("")
            setTitleErrorMessage("")
            setDescription("")
            setPrice("")
            setPhotoListErrorMessage("")
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
                                    maxWidth = 100.pct
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
                    tr {
                        th {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            +"Tags"
                        }
                        td {
                            css {
                                border = Border(1.px, LineStyle.solid, Color("black"))
                            }
                            tagListComponent {
                                onTagsChange = {
                                    setTagList(it)
                                }
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

    val (status, setStatus) = useState<Status>(Status.Published)
    val (errorMessage, setErrorMessage) = useState("")
    modalComponent {
        val modalSubmitHandler: FormEventHandler<HTMLFormElement> = {
            console.log("asdf category=${category.name}")
            console.log("asdf title=$title")
            console.log("asdf price=$price")
            console.log("asdf status=$status")
            scope.launch {
                val response = addItem(
                    ItemModel(
                        itemId = "",
                        category = category,
                        type = Type.Product,
                        title = title,
                        description = description,
                        price = price.toLong(),
                        photos = photoList,
                        status = Status.Draft,
                        tags = tagList,
                        reviews = emptyList(),
                        createDate = "",
                        publishDate = "",
                        lastUpdateDate = ""
                    )
                )
//                if (response.status != HttpStatusCode.OK) {
//                    setErrorMessage("")
//                }
            }
            it.preventDefault()
        }

        headerTitle = "New Item is about to be created!"
        content = VFC {
            form {
                onSubmit = modalSubmitHandler

                select {
                    css {
                        padding = 8.px
                    }
                    onChange = {
                        val chosenStatus = getAllStatus()[it.target.value.toInt()]
                        setStatus(chosenStatus)
                    }
                    option {
                        value = 0
                        +"Publish"
                    }
                    option {
                        value = 1
                        +"Save as Draft"
                    }
                }
                +"  AND"
                br {}
                div {
                    css {
                        display = Display.flex
                        flexDirection = FlexDirection.row
                        justifyContent = JustifyContent.spaceBetween
                    }
                    input {
                        css {
                            padding = 8.px
                        }
                        type = InputType.submit
                        value = "Go back"
                    }
                    input {
                        css {
                            padding = 8.px
                        }
                        type = InputType.submit
                        value = "Go next"
                    }
                }
                br {}
                br {}
                span {
                    css {
                        color = Color("red")
                    }
                    +errorMessage
                }
            }
        }
    }
}

private fun showModal() {
    val modalContainer = document.getElementById("modal-container") as HTMLDivElement
    modalContainer.style.display = Display.block.toString()
}