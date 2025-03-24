package com.smart_conspect.data.repositories

import at.favre.lib.crypto.bcrypt.BCrypt
import com.smart_conspect.data.models.auth.User
import com.smart_conspect.data.models.auth.UserType
import com.smart_conspect.data.tables.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent

object UserRepository: KoinComponent {

    fun findById(id: String): User? {
        return transaction {
            UserTable.selectAll().where { UserTable.id.eq(id) }
                .map {
                    User(
                        id = it[UserTable.id],
                        email = it[UserTable.email],
                        password = it[UserTable.password],
                        firstName = it[UserTable.firstName],
                        lastName = it[UserTable.lastName],
                        role = it[UserTable.role],
                        isActive = it[UserTable.isActive]
                    )
                }.firstOrNull()
        }
    }

    fun findByEmail(email: String): User? {
        return transaction {
            UserTable.selectAll().where { UserTable.email eq email }
                .map {
                    User(
                        id = it[UserTable.id],
                        email = it[UserTable.email],
                        password = it[UserTable.password],
                        firstName = it[UserTable.firstName],
                        lastName = it[UserTable.lastName],
                        role = it[UserTable.role],
                        isActive = it[UserTable.isActive]
                    )
                }.firstOrNull()
        }
    }

    fun createUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        role: UserType
    ): User {
        val userId = transaction {
            UserTable.insert {
                it[id] = java.util.UUID.randomUUID().toString()
                it[this.email] = email
                it[this.password] = hashPassword(password)
                it[this.firstName] = firstName
                it[this.lastName] = lastName
                it[this.role] = role
                it[isActive] = true
            } get UserTable.id
        }

        return User(
            id = userId,
            email = email,
            password = hashPassword(password),
            firstName = firstName,
            lastName = lastName,
            role = role,
            isActive = true
        )
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    fun verifyPassword(hashedPassword: String, inputPassword: String): Boolean {
        return BCrypt.verifyer().verify(inputPassword.toCharArray(), hashedPassword).verified
    }
}