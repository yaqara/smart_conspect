package com.smart_conspect.routing

import com.smart_conspect.data.models.assignments.AssignmentCreationRequest
import com.smart_conspect.data.repositories.AssignmentRepository
import com.smart_conspect.services.GridFSService
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import jdk.nashorn.internal.ir.Assignment
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.assignmentsRoutes() {
    val gridFSService: GridFSService by inject()
    val assignmentRepository: AssignmentRepository by inject()

    route("/assignments") {
        authenticate("auth-jwt") {
            post {
                println("Content-Type: ${call.request.contentType()}")
                println("123")
                val principal = call.principal<UserIdPrincipal>()
                println("Principal: $principal")

                if (principal == null) {
                    call.respond(HttpStatusCode.Unauthorized, "User not authenticated")
                    return@post
                }


                val multipartData = call.receiveMultipart()
                var assignmentRequest: AssignmentCreationRequest? = null
                val uploadedFiles = mutableListOf<PartData.FileItem>()

                try {
                    multipartData.forEachPart { part ->
                        when (part) {
                            is PartData.FormItem -> {
                                // Обрабатываем JSON-часть (assignment)
                                println("Handle FormItem")
                                if (part.name == "assignment") {
                                    println("Handle Assignment")
                                    assignmentRequest = Json.decodeFromString<AssignmentCreationRequest>(part.value)
                                }
                            }
                            is PartData.FileItem -> {
                                println("Handle FileItem")
                                // Обрабатываем файлы
                                uploadedFiles.add(part)
                            }
                            else -> {
                                // Пропускаем другие части
                            }
                        }
                        part.dispose()
                    }
                } catch (e: Exception) {
                    println(e)
                    call.respond(HttpStatusCode.BadRequest, "Invalid multipart data: ${e.message}")
                    return@post
                }

                if (assignmentRequest == null) {
                    call.respond(HttpStatusCode.BadRequest, "Missing or invalid 'assignment' field")
                    return@post
                }

                println("Received assignment: $assignmentRequest")

                // Далее обрабатываем файлы и создаем задание
                try {
                    val fileIds = gridFSService.handleMultipartData("userId", uploadedFiles)
                    val assignmentId = assignmentRepository.createAssignment(
                        userId = "userId",
                        classId = assignmentRequest!!.classId,
                        title = assignmentRequest!!.title,
                        description = assignmentRequest!!.description,
                        deadline = assignmentRequest!!.deadline,
                        fileIds = fileIds.map { it["fileId"]!! }
                    )

                    call.respond(
                        HttpStatusCode.Created, mapOf(
                            "assignmentId" to assignmentId,
                            "uploadedFiles" to fileIds
                        )
                    )
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Error processing request: ${e.message}")
                }
            }
        }
    }
}