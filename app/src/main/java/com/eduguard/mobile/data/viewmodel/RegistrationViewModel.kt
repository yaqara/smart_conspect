package com.eduguard.mobile.data.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {
    private val _login = mutableStateOf("")
    val login get() = _login

    private val _email = mutableStateOf("")
    val email get() = _email

    private val _password = mutableStateOf("")
    val password get() = _password

    fun setLogin(newLogin : String) { _login.value = newLogin }
    fun setEmail(newEmail : String) { _email.value = newEmail }
    fun setPassword(newPassword : String) { _login.value = newPassword }
}