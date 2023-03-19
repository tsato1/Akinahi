package com.tsato.pages.item

import com.tsato.components.footerComponent
import com.tsato.components.headerComponent
import com.tsato.components.item.createNewItemComponent
import react.VFC

val createNewItemPage = VFC {
    headerComponent {}
    createNewItemComponent {}
    footerComponent {}
}