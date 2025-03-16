package com.eduguard.mobile.ui.screen.subject_selector

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun SubjectSelectorScreen(navController: NavHostController) {
    // Настройка цветовой схемы MaterialTheme
    val blueColor = Color(0xFF1E88E5) // Синий цвет
    val whiteColor = Color.White // Белый цвет

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = blueColor,
            onPrimary = whiteColor,
            surface = whiteColor,
            onSurface = blueColor
        )
    ) {
        var subjects by remember { mutableStateOf(listOf<String>()) }
        var newSubjectName by remember { mutableStateOf("") }
        var isAddingSubject by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current
        Scaffold(
        ) { ip ->
            Column(
                modifier = Modifier
                    .padding(ip)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        isAddingSubject = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blueColor,
                        contentColor = whiteColor
                    ),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить предмет")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Добавить предмет")
                }

                Spacer(modifier = Modifier.height(16.dp))

                AnimatedVisibility(
                    visible = isAddingSubject,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            value = newSubjectName,
                            onValueChange = { newSubjectName = it },
                            label = { Text("Введите название предмета") },
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    if (newSubjectName.isNotBlank()) {
                                        subjects = subjects + newSubjectName
                                        newSubjectName = ""
                                        isAddingSubject = false
                                    }
                                    focusManager.clearFocus()
                                }
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = whiteColor,
                                focusedIndicatorColor = blueColor,
                                unfocusedIndicatorColor = blueColor.copy(alpha = 0.5f),
                                cursorColor = blueColor
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(
                                onClick = {
                                    isAddingSubject = false
                                    newSubjectName = ""
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = blueColor)
                            ) {
                                Text("Отмена")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = {
                                    if (newSubjectName.isNotBlank()) {
                                        subjects = subjects + newSubjectName
                                        newSubjectName = ""
                                        isAddingSubject = false
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = blueColor,
                                    contentColor = whiteColor
                                )
                            ) {
                                Text("Сохранить")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(subjects) { subject ->
                        SubjectItem(
                            subject = subject,
                            blueColor = blueColor,
                            whiteColor = whiteColor,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectItem(subject: String, blueColor: Color, whiteColor: Color , navController: NavHostController) {

    Card(
        onClick = {
            navController.navigate("home")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = blueColor)
    ) {
        Text(
            text = subject,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.titleMedium,
            color = whiteColor
        )
    }
}