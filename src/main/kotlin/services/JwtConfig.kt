package com.smart_conspect.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.smart_conspect.data.models.auth.UserType
import java.time.LocalDateTime
import java.time.ZoneOffset

object JwtConfig {
    private const val SECRET = "your-secret-key"
    private const val ISSUER = "https://jwt-provider-domain/"
    private val algorithm = Algorithm.HMAC512(SECRET)
    private const val AUDIENCE = "ktor-sample-app"
    private const val REALM = "ktor.io"

    private val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(ISSUER)
        .build()

    fun generateToken(userId : String, userType: UserType): String {
        return JWT.create()
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .withSubject(userId)
            .withClaim("role", userType.name)
            .withExpiresAt(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))
            .sign(algorithm)
    }

    fun getVerifier(): JWTVerifier = verifier
}