package com.smart_conspect.data.tables

import com.smart_conspect.data.models.auth.UserType
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.UUID

object UserTable : Table("users") {
    val id : Column<String> = varchar(name = "id", length = 36).default(UUID.randomUUID().toString())
    val email : Column<String> = varchar(name = "email", length = 255)
    val password : Column<String> = varchar(name = "password", length = 255)
    val firstName : Column<String> = varchar(name = "first_name", length = 255)
    val lastName : Column<String> = varchar(name = "last_name", length = 255)
    val role : Column<UserType> = enumeration("role", UserType::class)
    val isActive: Column<Boolean> = bool(name = "is_active")

    override val primaryKey : PrimaryKey = PrimaryKey(id)
}