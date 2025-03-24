package com.smart_conspect.routing

import com.smart_conspect.services.GridFSService
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.types.ObjectId

fun Route.gridFSRoutes() {
    val gridFSService = GridFSService()

    route("/files") {
        // Маршрут для загрузки файла
        post("/upload") {
            val multipartData = call.receiveMultipart()
            val uploadedFiles = mutableListOf<Map<String, String>>() // Список загруженных файлов
            val userId = "userId"

            multipartData.forEachPart { part ->
                if (part is PartData.FileItem) {
                    val fileName = part.originalFileName ?: "unknown"
                    val fileBytes = part.streamProvider().readBytes()

                    // Сохраняем файл в GridFS
                    val fileId = gridFSService.uploadFile(userId, fileName, fileBytes)

                    // Добавляем информацию о файле в список
                    uploadedFiles.add(mapOf(
                        "fileName" to fileName,
                        "fileId" to fileId.toString()
                    ))
                } else if (part is PartData.FormItem) {
                    // Обработка текстовых полей, если они есть
                    println("Received form field: ${part.name} = ${part.value}")
                }
                part.dispose()
            }

            // Возвращаем список загруженных файлов
            call.respond(mapOf("uploadedFiles" to uploadedFiles))
        }

        // Маршрут для скачивания файла
        get("/download/{id}") {
            val fileId = ObjectId(call.parameters["id"])
            val userId = "userId"
            val fileContent = gridFSService.downloadFile(userId, fileId)

            call.response.header(HttpHeaders.ContentDisposition, "attachment; filename=\"file\"")
            call.respondBytes(fileContent)
        }
    }
}