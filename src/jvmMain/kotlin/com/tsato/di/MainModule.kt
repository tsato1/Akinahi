package com.tsato.di

import com.tsato.data.local.ItemDataSourceImpl
import com.tsato.domain.ItemDataSource
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient()
            .coroutine
            .getDatabase("AkinahiMainDB")
    }

    single<ItemDataSource> {
        ItemDataSourceImpl(get())
    }
}
