package com.smart_conspect.routing

import com.smart_conspect.data.models.auth.LoginRequest
import com.smart_conspect.data.models.auth.RegisterRequest
import com.smart_conspect.services.AuthService
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authenticationRoutes() {
    route("/auth") {
        get {
            call.respondText("Hello World!")
        }
        authenticate("auth-jwt") {
            get("/123") {
                call.respondText { "Hello World!" }
            }
        }

        post("/register") {
            val request = call.receive<RegisterRequest>()

            val authService by inject<AuthService>()
            val response = authService.register(
                request = RegisterRequest(
                    email = request.email,
                    password = request.password,
                    firstName = request.firstName,
                    lastName = request.lastName,
                    role = request.role
                )
            )
            call.respond(response)
        }

        // Маршрут для входа
        post("/login") {
            val request = call.receive<LoginRequest>()

            val authService by inject<AuthService>()
            val response = authService.login(request)
            call.respond(response)
        }
    }
}