package com.eduguard.mobile.data.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _email = mutableStateOf("")
    val email get() = _email

    private val _password = mutableStateOf("")
    val password get() = _password

    fun setEmail(newEmail : String) {
        _email.value = newEmail
    }
    fun setPassword(newPassword : String) {
        _password.value = newPassword
    }
}