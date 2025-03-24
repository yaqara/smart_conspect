package com.smart_conspect.services

import com.smart_conspect.data.models.auth.LoginRequest
import com.smart_conspect.data.models.auth.AuthResponse
import com.smart_conspect.data.models.auth.RegisterRequest
import com.smart_conspect.data.models.auth.UserType
import com.smart_conspect.data.repositories.UserRepository

class AuthService(private val userRepository: UserRepository) {

    fun register(
        request: RegisterRequest
    ): AuthResponse {
        // Определяем роль пользователя
        val userRole = when (request.role.uppercase()) {
            "TEACHER" -> UserType.TEACHER
            "STUDENT" -> UserType.STUDENT
            else -> throw IllegalArgumentException("Invalid role")
        }

        // Создаем пользователя в базе данных
        val createdUser = userRepository.createUser(
            email = request.email,
            password = request.password,
            firstName = request.firstName,
            lastName = request.lastName,
            role = userRole
        )

        // Генерируем JWT-токен
        val token = JwtConfig.generateToken(createdUser.id, createdUser.role)

        // Возвращаем ответ с токеном и данными пользователя
        return AuthResponse(
            token = token,
            user = createdUser.copy(password = "") // Удаляем пароль из ответа
        )
    }

    fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("User not found")

        if (!userRepository.verifyPassword(user.password, request.password)) {
            throw IllegalArgumentException("Invalid credentials")
        }

        val token = JwtConfig.generateToken(user.id, user.role)
        return AuthResponse(token = token, user = user.copy(password = ""))
    }
}