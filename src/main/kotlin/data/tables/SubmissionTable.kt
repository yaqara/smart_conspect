package com.smart_conspect.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object SubmissionTable : Table("submissions") {
    val id: Column<String> = varchar("id", length = 36).default(UUID.randomUUID().toString())
    val assignmentId: Column<String> = varchar("assignment_id", length = 36).references(AssignmentTable.id)
    val studentId: Column<String> = varchar("student_id", length = 36).references(UserTable.id)
    val fileIds: Column<String?> = text("file_ids").nullable() // JSON-массив fileId
    val submittedAt: Column<Long> = long("submitted_at")
    val grade: Column<Double?> = double("grade").nullable()
    val feedback: Column<String?> = text("feedback").nullable()

    override val primaryKey = PrimaryKey(id)
}