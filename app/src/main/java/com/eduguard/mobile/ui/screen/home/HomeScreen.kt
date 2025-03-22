package com.eduguard.mobile.ui.screen.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            Row(modifier = Modifier.padding(8.dp)) {
                Button(
                    onClick = { navController.navigate("book") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Учебник")
                }
                Button(
                    onClick = { navController.navigate("copybook") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Тетрадь")
                }
                Button(
                    onClick = { navController.navigate("menu") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Меню")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(text = "Это домашний экран", modifier = Modifier.padding(16.dp))
        }
    }
}