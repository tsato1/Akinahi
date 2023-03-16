package com.tsato.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeleteItemRequest(
    val itemId: String
)