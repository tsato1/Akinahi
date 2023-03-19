package com.tsato.components.item

import com.tsato.util.isTagCharsValid
import com.tsato.util.isTagValid
import csstype.*
import emotion.react.css
import maxNumCharsForTag
import maxNumTags
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.useState

external interface TagListProps: Props {
    var onTagsChange: (tagList: List<String>) -> Unit
}

val tagListComponent = FC<TagListProps> { props ->
    val (input, setInput) = useState("")
    val (tags, setTags) = useState<List<String>>(emptyList())
    val (isKeyReleased, setIsKeyReleased) = useState(false)
    val (isComposing, setIsComposing) = useState(false) // true means 'Japanese' is on
    val (errorMessage, setErrorMessage) = useState("")

    div {
        css {
            width = 100.pct
            maxWidth = 100.pct
            display = Display.flex
            flexDirection = FlexDirection.row
            justifyContent = JustifyContent.flexStart
            alignItems = AlignItems.center
            overflow = Overflow.scroll
            verticalAlign = VerticalAlign.middle
        }
        tags.forEachIndexed { i, tag ->
            div {
                css {
                    height = 30.px
                    marginLeft = 4.px
                    marginRight = 4.px
                    padding = 4.px
                    display = Display.flex
                    justifyContent = JustifyContent.center
                    alignItems = AlignItems.center
                    backgroundColor = Color("orange")
                    border = Border(1.px, LineStyle.solid, Color("orange"))
                    borderRadius = 4.px
                    whiteSpace = WhiteSpace.nowrap
                }
                span {
                    +"#$tag"
                }
                button {
                    css {
                        marginLeft = 4.px
                        backgroundColor = Color("orange")
                        cursor = Cursor.pointer
                    }
                    onClick = {
                        it.preventDefault()
                        val newTags = tags.filterIndexed { j, _ -> i != j }
                        setTags(newTags)
                    }
                    +"x"
                }
            }
        }
        input {
            css {
                maxWidth = 50.pct
                minWidth = 120.px
                marginTop = 8.px
                marginLeft = 4.px
                marginRight = 4.px
                marginBottom = 8.px
                padding = 8.px
                border = None.none
            }
            id = "input"
            value = input
            placeholder = "Enter a tag"
            onKeyUp = {
                setIsKeyReleased(true)
            }
            onKeyDown = {
                val key = it.key
                val trimmedInput = input.trim()

                if (key == "Enter" && it.currentTarget.value.isEmpty()) {
                    it.preventDefault()
                } else if ((key == "Enter" && !isComposing) &&
                    isTagValid(trimmedInput) &&
                    !tags.contains(trimmedInput)
                ) {
                    val newTags = tags.toMutableList()
                    newTags.add(trimmedInput)

                    if (newTags.size <= maxNumTags) {
                        setTags(newTags)
                        setInput("")
                        setErrorMessage("")
                    } else {
                        setErrorMessage("Max number of tags allowed is $maxNumTags")
                    }

                    it.preventDefault()
                }

                if (key == "Backspace" &&
                    tags.isNotEmpty() &&
                    input.isEmpty() &&
                    isKeyReleased
                ) {
                    val copiedTags = tags.toMutableList()
                    val poppedTag = copiedTags.removeLastOrNull()
                    poppedTag?.let {
                        setTags(copiedTags)
                        setInput(poppedTag)
                    }

                    it.preventDefault()
                }

                setIsKeyReleased(false)
            }
            onChange = {
                val text = it.target.value
                if (maxNumCharsForTag + 1 < text.length) {
                    setErrorMessage("The max number of characters for a tag is $maxNumCharsForTag")
                } else if (!isTagCharsValid(text)) {
                    setErrorMessage("Only a-z, A-Z, 0-9 are allowed.")
                } else {
                    setErrorMessage("")
                    setInput(it.target.value)
                }
            }
            onCompositionStart = {
                setIsComposing(true)
            }
            onCompositionEnd = {
                setIsComposing(false)
            }
            autoFocus = true
        }
    }
    p {
        css {
            color = Color("red")
        }
        +errorMessage
    }
}
