package com.smart_conspect.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.UUID

object AssignmentTable : Table("assignments") {
    val id: Column<String> = varchar("id", length = 36).default(UUID.randomUUID().toString())
    val classId: Column<String> = reference(name = "class_id", refColumn = ClassTable.id)
    val title: Column<String> = varchar("title", length = 255)
    val description: Column<String> = text("description")
    val deadline: Column<Long?> = long("deadline").nullable()
    val fileIds: Column<String?> = text("file_ids").nullable() // JSON-массив fileId
    val createdAt: Column<Long> = long("created_at")

    override val primaryKey = PrimaryKey(id)
}