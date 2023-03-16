package com.tsato.domain

import com.tsato.data.local.entities.ItemEntity

interface ItemDataSource {

    suspend fun getAllItemsByUserId(userId: String): List<ItemEntity>

    suspend fun insertItem(itemEntity: ItemEntity): Boolean

    suspend fun deleteItem(itemId: String): Boolean

}