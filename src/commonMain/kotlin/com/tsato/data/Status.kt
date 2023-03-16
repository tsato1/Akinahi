package com.tsato.data

import kotlinx.serialization.Serializable

@Serializable
sealed class Status {
    object Draft: Status()
    object Published: Status()
}