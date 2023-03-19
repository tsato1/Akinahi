package com.tsato.components.item

import com.tsato.data.Category
import react.FC
import react.Props
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select

external interface CategoryListProps : Props {
    var categoryList: List<Category>
    var onCategoryChange: (index: Int?) -> Unit
}

val categorySelectionComponent = FC<CategoryListProps> { props ->
    select {
        onChange = {
            props.onCategoryChange(it.target.value.toIntOrNull())
        }
        props.categoryList.forEachIndexed { i, category ->
            option {
                value = i
                +category.name
            }
        }
    }
}