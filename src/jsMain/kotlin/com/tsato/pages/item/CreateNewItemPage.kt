package com.tsato.pages.item

import com.tsato.components.item.createNewItemComponent
import com.tsato.components.footerComponent
import com.tsato.components.headerComponent
import react.VFC

val createNewItemPage = VFC {
    headerComponent {}
    createNewItemComponent {
        onSubmit = { category, title, description, price, photos ->
            console.log("asdf category = ${category.name}")
            console.log("asdf title = $title")
            console.log("asdf description = $description")
            console.log("asdf price = $price")
            console.log("asdf photos = $photos")
        }
    }
    footerComponent {}
}