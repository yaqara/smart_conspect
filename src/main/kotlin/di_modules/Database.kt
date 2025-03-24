package com.smart_conspect.di_modules

import com.mongodb.client.MongoClients
import com.smart_conspect.data.tables.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

import org.koin.dsl.module

val databaseModule = module {
    single {
        val mongoConfig = get<Application>().environment.config.config("mongodb")
        val mongoUri = mongoConfig.property("uri").getString()
        val mongoDatabaseName = mongoConfig.property("database").getString()

        MongoClients.create(mongoUri).getDatabase(mongoDatabaseName)
    }

    single {
        val dbConfig = get<Application>().environment.config.config("database")
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

        transaction {
            if (!SchemaUtils.listTables().contains("users")) {
                SchemaUtils.create(
                    UserTable,
                    AssignmentTable,
                    SubmissionTable,
                    ClassTable,
                    ClassStudentTable
                )
            }
        }
    }
}