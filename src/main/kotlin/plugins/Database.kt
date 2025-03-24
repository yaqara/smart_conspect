package com.smart_conspect.plugins

import com.mongodb.client.MongoClient
import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.client.gridfs.GridFSBuckets
import com.smart_conspect.data.tables.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.litote.kmongo.KMongo

fun Application.configurePostgresql() {
    val dbConfig = environment.config.config("postgres")
    val dbUrl = dbConfig.property("url").getString()
    val dbDriver = dbConfig.property("driver").getString()
    val dbUser = dbConfig.property("user").getString()
    val dbPassword = dbConfig.property("password").getString()

    Database.connect(
        url = dbUrl,
        driver = dbDriver,
        user = dbUser,
        password = dbPassword
    )

    // Создание таблиц, если они не существуют
    transaction {
        SchemaUtils.create(
            UserTable,
            AssignmentTable,
            SubmissionTable,
            ClassTable,
            ClassStudentTable
        )
    }
}

fun Application.configureMongoDB() {
    val dbConfig = environment.config.config("mongodb")
    val mongoUri = dbConfig.property("uri").getString()
    val databaseName = dbConfig.property("database").getString()

    // Создаем клиент MongoDB
    val client: MongoClient = KMongo.createClient(mongoUri)

    // Получаем базу данных
    val database = client.getDatabase(databaseName)

    // Инициализируем GridFS
    val gridFSBucket: GridFSBucket = GridFSBuckets.create(database)

    println("MongoDB connected successfully!")
}