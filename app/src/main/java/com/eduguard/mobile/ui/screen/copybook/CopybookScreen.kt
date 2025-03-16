package com.eduguard.mobile.ui.screen.copybook

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.eduguard.mobile.data.viewmodel.DrawingViewModel
import com.eduguard.mobile.ui.screen.split_screen.DrawingScreen

@Composable
fun CopybookScreen(
    navController: NavHostController,
    drawingVm: DrawingViewModel
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigate("split") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Раздельный экран")
                }
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Домой")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            DrawingScreen(
                vm = drawingVm,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}