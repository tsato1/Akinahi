package com.tsato.data.model

import com.tsato.data.Category
import com.tsato.data.Status
import kotlinx.serialization.Serializable

@Serializable
data class ItemModel(
    val itemId: String,
    val category: Category,
    val title: String,
    val description: String,
    val price: Long,
    val photos: List<Byte>,
    val status: Status,
    val publishDate: String,
    val updateDate: String
) {

    companion object {
        const val pathToItems = "/items"
        const val pathToItem = "/item"
    }

}