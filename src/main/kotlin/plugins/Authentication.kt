package com.smart_conspect.plugins

import com.smart_conspect.data.repositories.UserRepository
import com.smart_conspect.services.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureJWT() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "ktor.io"
            verifier(JwtConfig.getVerifier())
            validate {
                val payload = it.payload
                val userId = payload.subject
                println("Validating token with userId: $userId")
                val user = UserRepository.findById(userId)
                if (user != null) {
                    println("User found: ${user.email}")
                    UserIdPrincipal(userId)
                } else {
                    println("User not found for userId: $userId")
                    null
                }
            }
        }
    }
}