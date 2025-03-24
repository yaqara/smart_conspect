package com.smart_conspect.data.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val role: UserType,
    val isActive: Boolean
)