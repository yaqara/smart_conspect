package com.smart_conspect.data.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import java.util.*

object ClassTable : Table() {
    val id : Column<String> = varchar(name = "id", length = 36).default(UUID.randomUUID().toString())
    val name : Column<String> = varchar(name = "name", length = 255)
    val ownerId = reference("owner_id", UserTable.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey : PrimaryKey = PrimaryKey(id)
}