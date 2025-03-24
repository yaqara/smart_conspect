package com.eduguard.mobile.ui.screen.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AuthenticationScreen(
    onAuth : () -> Unit
) {
    var registeredEarlier by remember { mutableStateOf(true) }

    if (registeredEarlier) {
        LoginScreen(onChangeMethod = { registeredEarlier = false }) { onAuth() }
    } else {
        RegistrationScreen(onChangeMethod = { registeredEarlier = true }) { onAuth() }
    }
}