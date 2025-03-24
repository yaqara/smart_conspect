package com.smart_conspect.data.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val user: User
)
