package com.smart_conspect.routing

import com.smart_conspect.data.models.classes.ClassCreationRequest
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.classesRoutes() {
    route("/classes") {
        authenticate("auth-jwt") {
            post {
                val userId = call.principal<UserIdPrincipal>()?.name
                    ?: return@post call.respond(HttpStatusCode.Unauthorized, "User not authenticated")
                val classRequest = call.receive<ClassCreationRequest>()


            }
        }
    }
}