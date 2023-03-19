package com.tsato.data.model

import com.tsato.data.Category
import com.tsato.data.Status
import com.tsato.data.Type
import kotlinx.serialization.Serializable

@Serializable
data class ItemModel(
    val itemId: String,
    val category: Category,
    val type: Type,
    val title: String,
    val description: String,
    val price: Long,
    val photos: List<ByteArray>,
    val status: Status,
    val tags: List<String>,
    val reviews: List<String>,
    val createDate: String,
    val publishDate: String,
    val lastUpdateDate: String
) {

    companion object {
        const val pathToItems = "/items"
        const val pathToItem = "/item"
    }

}