package com.tsato.data.local

import com.tsato.data.local.entities.ItemEntity
import com.tsato.domain.ItemDataSource
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ItemDataSourceImpl(
    database: CoroutineDatabase
) : ItemDataSource {

    private val items = database.getCollection<ItemEntity>()

    override suspend fun getAllItemsByUserId(userId: String): List<ItemEntity> {
        return items.find(ItemEntity::userId eq userId).toList()
    }

    override suspend fun insertItem(itemEntity: ItemEntity): Boolean {
        return items.insertOne(itemEntity).wasAcknowledged()
    }

    override suspend fun deleteItem(itemId: String): Boolean {
        return items.deleteOne(ItemEntity::itemId eq itemId).wasAcknowledged()
    }

}