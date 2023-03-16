package com.tsato.components.item

import com.tsato.data.Category
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import web.html.HTMLSelectElement

external interface CategoryListProps : Props {
    var categoryList: List<Category>
    var onCategoryChange: (index: Int?) -> Unit
}

val categorySelectionComponent = FC<CategoryListProps> { props ->
    val categoryOnChangeHandler: ChangeEventHandler<HTMLSelectElement> = {
        props.onCategoryChange(it.target.value.toIntOrNull())
    }

    select {
        onChange = categoryOnChangeHandler
        props.categoryList.forEachIndexed { i, category ->
            option {
                value = i
                +category.name
            }
        }
    }
}