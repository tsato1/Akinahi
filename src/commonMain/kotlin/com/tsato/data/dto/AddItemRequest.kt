package com.tsato.data.dto

import com.tsato.data.model.ItemModel
import kotlinx.serialization.Serializable

@Serializable
data class AddItemRequest(
    val itemModel: ItemModel
)