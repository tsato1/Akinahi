package com.tsato.data

import kotlinx.serialization.Serializable

@Serializable
sealed class Type(val name: String) {
    object Shop: Type("Shop")
    object Product: Type("Product")
    data class Event(val eventDate: String): Type("Event")
}