package com.eduguard.mobile.ui.screen.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduguard.mobile.data.viewmodel.RegistrationViewModel

@Composable
fun RegistrationScreen(
    registrationVm : RegistrationViewModel = viewModel(),
    onChangeMethod : () -> Unit,
    onAuth : () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
//        // Фон с изображением
//        Image(
//            painter = painterResource(id = R.drawable.ic_launcher_background),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop // Масштабирование изображения
//        )


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center // Центрирование содержимого по горизонтали и вертикали
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp), // Закругленные углы формы,
                shadowElevation = 20.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    // Здесь можно добавить элементы формы, например TextField и Button
                    RegistrationForm(registrationVm = registrationVm, onAuth = onAuth)
                    Text(
                        modifier = Modifier
                            .clickable {
                                onChangeMethod()
                            },
                        text = "Есть аккаунт? Войти сейчас!",
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun RegistrationForm(
    registrationVm : RegistrationViewModel,
    onAuth : () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                shape = RoundedCornerShape(16.dp),
                value = registrationVm.login.value,
                onValueChange = { registrationVm.setLogin(it) },
                placeholder = {
                    Text(
                        text = "Имя пользователя",
                        fontSize = 12.sp
                    )
                }
            )
            OutlinedTextField(
                shape = RoundedCornerShape(16.dp),
                value = registrationVm.email.value,
                onValueChange = { registrationVm.setEmail(it) },
                placeholder = {
                    Text(
                        text = "Эл. почта",
                        fontSize = 12.sp
                    )
                }
            )
            OutlinedTextField(
                shape = RoundedCornerShape(16.dp),
                value = registrationVm.password.value,
                onValueChange = { registrationVm.setPassword(it) },
                placeholder = {
                    Text(
                        text = "Пароль",
                        fontSize = 12.sp
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black
                )
            )
            OutlinedButton(
                onClick = onAuth,
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp)
            ) { Text(text = "Войти") }
        }
    }
}