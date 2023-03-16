package com.tsato

import com.tsato.data.dto.LoginRequest
import com.tsato.data.dto.RegisterRequest
import com.tsato.data.model.ItemModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

val jsonClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun getItems(): List<ItemModel> {
    return jsonClient.get("http://0.0.0.0:9090/api" + ItemModel.pathToItems).body()
}

suspend fun addItem(item: ItemModel) {
    jsonClient.post("http://0.0.0.0:9090/api" + ItemModel.pathToItem) {
        contentType(ContentType.Application.Json)
        setBody(item)
    }
}

suspend fun deleteItem(item: ItemModel) {
    jsonClient.delete("http://0.0.0.0:9090/api" + ItemModel.pathToItem + "/${item.itemId}")
}

suspend fun register(registerRequest: RegisterRequest): HttpResponse {
    return jsonClient.post("http://0.0.0.0:9090" + "/auth/register") {
        userAgent("Web")
        contentType(ContentType.Application.Json)
        setBody(registerRequest)
    }
}

suspend fun login(loginRequest: LoginRequest): HttpResponse {
    return jsonClient.post("http://0.0.0.0:9090" + "/auth/login") {
        userAgent("Web")
        contentType(ContentType.Application.Json)
        setBody(loginRequest)
    }
}