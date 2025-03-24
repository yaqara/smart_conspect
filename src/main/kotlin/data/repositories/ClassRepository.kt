package com.smart_conspect.data.repositories

import com.smart_conspect.data.tables.ClassTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent

object ClassRepository : KoinComponent {
    fun createClass(
        name: String,
        ownerId: String
    ) {
        transaction {
            ClassTable.insert {
                it[this.name] = name
                it[this.ownerId] = ownerId
            }
        }
        return
    }
}