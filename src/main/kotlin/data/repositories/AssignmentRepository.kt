package com.smart_conspect.data.repositories

import com.smart_conspect.data.tables.AssignmentTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import java.util.*

object AssignmentRepository : KoinComponent {

    fun createAssignment(
        userId: String,
        classId: String,
        title: String,
        description: String,
        deadline: Long?,
        fileIds: List<String>
    ): String {
        val assignmentId = UUID.randomUUID().toString()

        transaction {
            AssignmentTable.insert {
                it[id] = assignmentId
                it[this.classId] = classId
                it[this.title] = title
                it[this.description] = description
                it[this.deadline] = deadline
                it[this.fileIds] = if (fileIds.isNotEmpty()) fileIds.joinToString(",") else null
                it[createdAt] = System.currentTimeMillis()
            }
        }

        return assignmentId
    }
}