package com.tsato.data.local.entities

import com.tsato.data.Category
import com.tsato.data.Status
import com.tsato.data.Type
import com.tsato.data.model.ItemModel
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class ItemEntity(
    val itemId: String,
    val userId: String,
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
    val lastUpdateDate: String,
    @BsonId val id: String = ObjectId().toString()
) {
    fun toItemModel() = ItemModel(
        itemId = itemId,
        category = category,
        type = type,
        title = title,
        description = description,
        price = price,
        photos = photos,
        status = status,
        tags = tags,
        reviews = reviews,
        createDate = createDate,
        publishDate = publishDate,
        lastUpdateDate = lastUpdateDate
    )
}

fun ItemModel.toItemEntity(userId: String) = ItemEntity(
    itemId = itemId,
    userId = userId,
    category = category,
    type = type,
    title = title,
    description = description,
    price = price,
    photos = photos,
    status = status,
    tags = tags,
    reviews = reviews,
    createDate = createDate,
    publishDate = publishDate,
    lastUpdateDate = lastUpdateDate
)