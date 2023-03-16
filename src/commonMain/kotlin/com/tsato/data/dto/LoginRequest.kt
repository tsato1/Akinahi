package com.tsato.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    val appId: String? = null,
    val email: String,
    val password: String,
    val role: String,
    val is2FAOn: Boolean,
    val phone: String? = null,
    val media: String
)