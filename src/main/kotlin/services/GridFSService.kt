package com.smart_conspect.services

import com.mongodb.client.gridfs.model.GridFSFile
import io.ktor.http.content.*
import org.bson.BsonObjectId
import org.bson.types.ObjectId
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class GridFSService {

    // Загрузка файла в GridFS
    fun uploadFile(userId: String, fileName: String, content: ByteArray): ObjectId {
        println("Uploading file: $fileName")
        val fileId = ObjectId() // Генерируем уникальный ID для файла
        val filePath = "$userId/$fileId" // Формируем путь userId/fileId
        println("Uploading file with path: $filePath")

        val inputStream = ByteArrayInputStream(content)
        MongoConfig.gridFSBucket.uploadFromStream(filePath, inputStream) // Используем путь как filename
        println("File uploaded successfully. Path: $filePath, ID: $fileId")
        return fileId
    }

    // Download a file from GridFS
    fun downloadFile(userId: String, fileId: ObjectId): ByteArray {
        val filePath = "$userId/$fileId" // Формируем путь userId/fileId
        println("Downloading file with path: $filePath")

        val outputStream = ByteArrayOutputStream()
        val gridFSFile = MongoConfig.gridFSBucket.find().firstOrNull { it.filename == filePath }
            ?: throw IllegalArgumentException("File not found for path: $filePath")

        MongoConfig.gridFSBucket.downloadToStream(gridFSFile.id.asObjectId().value, outputStream)
        return outputStream.toByteArray()
    }

    fun deleteFile(userId: String, fileId: ObjectId) {
        val filePath = "$userId/$fileId" // Формируем путь userId/fileId
        println("Deleting file with path: $filePath")

        val gridFSFile = MongoConfig.gridFSBucket.find().firstOrNull { it.filename == filePath }
            ?: throw IllegalArgumentException("File not found for path: $filePath")

        MongoConfig.gridFSBucket.delete(gridFSFile.id)
        println("File deleted successfully. Path: $filePath")
    }

    suspend fun handleMultipartData(
        userId: String,
        files: List<PartData.FileItem>
    ): List<Map<String, String>> {
        val uploadedFiles = mutableListOf<Map<String, String>>()

        for (part in files) {
            val fileName = part.originalFileName ?: "unknown"
            val fileBytes = part.streamProvider().readBytes()

            // Загружаем файл в GridFS
            val fileId = uploadFile(userId, fileName, fileBytes)

            // Добавляем информацию о файле в список
            uploadedFiles.add(
                mapOf(
                    "fileName" to fileName,
                    "fileId" to fileId.toString()
                )
            )
        }

        return uploadedFiles
    }

    // Получение информации о файле по ID
    fun getFileById(fileId: ObjectId): GridFSFile? {
        return MongoConfig.gridFSBucket.find().firstOrNull { compareObjectIds(it.id.asObjectId(), fileId) }
    }

    fun compareObjectIds(bsonObjectId: BsonObjectId?, objectId: ObjectId?): Boolean {
        // Проверяем, что оба объекта не null
        if (bsonObjectId == null || objectId == null) {
            return false
        }

        // Сравниваем значения через метод equals
        return bsonObjectId.value == objectId
    }
}