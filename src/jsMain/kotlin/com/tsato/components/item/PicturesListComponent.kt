package com.tsato.components.item

import com.tsato.util.ValidationException
import com.tsato.util.validatePhoto
import csstype.*
import emotion.react.css
import js.core.asList
import js.core.get
import maxNumPhotos
import pathToAddPhotoIcon
import pathToCloseIcon
import pathToDefaultPhoto
import pathToLoadingIcon
import photoThumbnailSize
import react.VFC
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul
import react.useState
import web.dom.document
import web.file.FileReader
import web.html.HTMLImageElement
import web.html.HTMLInputElement
import web.html.Image
import web.html.InputType

val photoListComponent = VFC {
    var clickedPhotoIndex = 0 // 1 <= clickedPhotoIndex <= maxNumPhotos(=10)
    var draggedPhotoId: String? = null // ex. "item-2"

    val (errorMessage, setErrorMessage) = useState("")

    ul {
        css {
            maxWidth = 1000.px
        }
        id = "ul"
        for (i in 1..maxNumPhotos) {
            li {
                css {
                    width = photoThumbnailSize.px
                    height = photoThumbnailSize.px
                    marginRight = 10.px
                    border = Border(1.px, LineStyle.solid)
                    listStyleType = None.none
                    display = Display.inlineBlock
                    position = Position.relative
                    top = 0.px
                    left = 0.px
                    cursor = Cursor.grab
                    boxShadow = BoxShadow(0.px, 0.px, 0.px, 0.px, Color(""))
                }
                id = "item-$i"
                draggable = true
                onDragStart = {
                    draggedPhotoId = it.currentTarget.id

                    it.dataTransfer.setData("text/plain", this.id.toString())
                    it.currentTarget.style.cursor = Cursor.grabbing.toString()
                }
                onDragOver = {
                    it.preventDefault()

                    it.currentTarget.style.apply {
                        if (draggedPhotoId != it.currentTarget.id) {
                            boxShadow = BoxShadow(0.px, 0.px, 0.px, 4.px, Color("red")).toString()
                        }
                    }
                }
                onDragLeave = {
                    it.currentTarget.style.apply {
                        boxShadow = BoxShadow(0.px, 0.px, 0.px, 0.px, Color("")).toString()
                    }
                }
                onDrop = {
                    it.preventDefault()

                    it.currentTarget.style.apply {
                        cursor = Cursor.grab.toString()
                        boxShadow = BoxShadow(0.px, 0.px, 0.px, 0.px, Color("")).toString()
                    }

                    draggedPhotoId?.let { id ->
                        val original = document.getElementById(id)!!

                        if (it.currentTarget.id != id) {
                            val parent = original.parentNode!!
                            val cloned = original.cloneNode(true)

                            parent.insertBefore(cloned, original)
                            parent.insertBefore(original, it.currentTarget)
                            parent.replaceChild(it.currentTarget, cloned)
                        }

                        original.style.apply {
                            cursor = Cursor.grab.toString()
                        }
                    }
                    draggedPhotoId = null
                }
                img { // photo
                    css {
                        width = photoThumbnailSize.px
                        height = photoThumbnailSize.px
                        position = Position.relative
                        top = 0.px
                        left = 0.px
                        right = 0.px
                        bottom = 0.px
                        marginTop = Auto.auto
                        marginLeft = Auto.auto
                        marginRight = Auto.auto
                        marginBottom = Auto.auto
                    }
                    id = "photo-$i"
                    src = pathToDefaultPhoto
                    draggable = false
                }
                img { // discard photo button
                    css {
                        width = 24.px
                        height = 24.px
                        backgroundColor = Color("white")
                        position = Position.absolute
                        top = 0.px
                        left = 0.px
                        display = None.none
                        hover {
                            cursor = Cursor.pointer
                        }
                    }
                    id = "discard-btn-$i"
                    src = pathToCloseIcon
                    draggable = false
                    onClick = {
                        val photoImg = document.getElementById("photo-$i") as HTMLImageElement
                        photoImg.src = pathToDefaultPhoto

                        val discardBtn = document.getElementById("discard-btn-$i") as HTMLImageElement
                        discardBtn.style.display = None.none.toString()

                        val addPhotoBtn = document.getElementById("add-photo-btn-$i") as HTMLImageElement
                        addPhotoBtn.style.display = Display.block.toString()
                    }
                }
                img { // add photo button
                    css {
                        width = 24.px
                        height = 24.px
                        alignSelf = AlignSelf.center
                        position = Position.absolute
                        top = 0.px
                        left = 0.px
                        right = 0.px
                        bottom = 0.px
                        marginTop = Auto.auto
                        marginLeft = Auto.auto
                        marginRight = Auto.auto
                        marginBottom = Auto.auto
                        hover {
                            cursor = Cursor.pointer
                        }
                        display = Display.block
                    }
                    id = "add-photo-btn-$i"
                    src = pathToAddPhotoIcon
                    draggable = false
                    onClick = {
                        clickedPhotoIndex = i
                        val fileUpload = document.getElementById("file-upload") as HTMLInputElement
                        fileUpload.click()
                        it.preventDefault()
                    }
                }
            }
        }
    }
    input {
        css {
            display = None.none
        }
        id = "file-upload"
        type = InputType.file
        accept = ".png, .jpeg, .jpg"
        multiple
        onChange = {
            val photoImg = document.getElementById("photo-$clickedPhotoIndex") as HTMLImageElement
            val discardBtn = document.getElementById("discard-btn-$clickedPhotoIndex") as HTMLImageElement
            val addPhotoBtn = document.getElementById("add-photo-btn-$clickedPhotoIndex") as HTMLImageElement

            it.target.files?.let { files ->
                if (files.asList().isNotEmpty()) {
                    val reader = FileReader()
                    reader.readAsDataURL(files[0])
                    reader.onloadstart = {
                        photoImg.src = pathToLoadingIcon
                    }
                    reader.onload = {
                        val img = Image()
                        img.src = reader.result.toString()
                        img.onload = {
                            try {
                                validatePhoto(img)

                                /* The photo is valid */
                                photoImg.src = reader.result.toString()
                                discardBtn.style.display = Display.block.toString()
                                addPhotoBtn.style.display = None.none.toString()
                                setErrorMessage("")
                            } catch (error: ValidationException) {
                                photoImg.src = pathToDefaultPhoto
                                setErrorMessage(error.message ?: "Unknown error")
                            }
                        }
                    }
                }
            }
        }
    }
    span {
        css {
            color = Color("red")
        }
        +errorMessage
    }
}