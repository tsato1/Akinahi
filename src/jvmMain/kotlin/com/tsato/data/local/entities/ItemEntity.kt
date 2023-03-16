package com.tsato.data.local.entities

import com.tsato.data.Category
import com.tsato.data.Status
import com.tsato.data.model.ItemModel
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class ItemEntity(
    val itemId: String,
    val userId: String,
    val category: Category,
    val title: String,
    val description: String,
    val price: Long,
    val photos: List<Byte>,
    val status: Status,
    val publishDate: String,
    val updateDate: String,
    @BsonId val id: String = ObjectId().toString()
) {
    fun toItemModel() = ItemModel(
        itemId = itemId,
        category = category,
        title = title,
        description = description,
        price = price,
        photos = photos,
        status = status,
        publishDate = publishDate,
        updateDate = updateDate
    )
}

fun ItemModel.toItemEntity(userId: String) = ItemEntity(
    itemId = itemId,
    userId = userId,
    category = category,
    title = title,
    description = description,
    price = price,
    photos = photos,
    status = status,
    publishDate = publishDate,
    updateDate = updateDate
)