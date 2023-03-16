package com.tsato.routes

import com.tsato.data.dto.AddItemRequest
import com.tsato.data.dto.DeleteItemRequest
import com.tsato.data.local.entities.toItemEntity
import com.tsato.domain.ItemDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.itemRoute() {
    val itemDataSource by inject<ItemDataSource>()

    route("item/{itemId}") {
        get {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            if (userId == null) {
                call.respond(HttpStatusCode.Conflict)
                return@get
            }

            val items = itemDataSource.getAllItemsByUserId(userId)
            call.respond(HttpStatusCode.OK, items)
        }

        put {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            if (userId == null) {
                call.respond(HttpStatusCode.Conflict)
                return@put
            }

            val request = try {
                call.receive<AddItemRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            if (itemDataSource.insertItem(request.itemModel.toItemEntity(userId))) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.Conflict)
            }
        }

        delete {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            if (userId == null) {
                call.respond(HttpStatusCode.Conflict)
                return@delete
            }

            val request = try {
                call.receive<DeleteItemRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            if (itemDataSource.deleteItem(request.itemId)) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.Conflict)
            }
        }
    }

    route("items") {
        get {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            if (userId == null) {
                call.respond(HttpStatusCode.Conflict)
                return@get
            }

            val items = itemDataSource.getAllItemsByUserId(userId)
            call.respond(HttpStatusCode.OK, items)
        }

//        post {
//            val request = try {
//                call.receive<AddItemRequest>()
//            } catch (e: ContentTransformationException) {
//                call.respond(HttpStatusCode.BadRequest)
//                return@post
//            }
//
//            if (itemDataSource.insertItem(request.item)) {
//                call.respond(HttpStatusCode.OK)
//            } else {
//                call.respond(HttpStatusCode.Conflict)
//            }
//        }

//        delete {
//            val request = try {
//                call.receive<DeleteItemRequest>()
//            } catch (e: ContentTransformationException) {
//                call.respond(HttpStatusCode.BadRequest)
//                return@delete
//            }
//
//            if (itemDataSource.deleteItem(request.itemId)) {
//                call.respond(HttpStatusCode.OK)
//            } else {
//                call.respond(HttpStatusCode.Conflict)
//            }
//        }
    }

}