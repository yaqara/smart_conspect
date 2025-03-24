package com.smart_conspect.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ClassStudentTable : Table() {
    val classId = reference("class_id", ClassTable.id)
    val studentId = reference("student_id", UserTable.id)
    val joinedAt : Column<Long> = long("joined_at")

    override val primaryKey: PrimaryKey = PrimaryKey(studentId, classId)
}