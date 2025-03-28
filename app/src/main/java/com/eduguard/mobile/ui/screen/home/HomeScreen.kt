package com.eduguard.mobile.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    subjectId : String,
) {
    val cards = listOf(
        ActionCardData("Конспект", null) { navController.navigate("subject/$subjectId/note") },
        ActionCardData("Учебники", null) { navController.navigate("subject/$subjectId/books") },
        ActionCardData("Домашние задания", null) { }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 30.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cards) { card ->
                ActionCard(
                    name = card.name,
                    icon = card.icon,
                    onClick = card.onClick
                )
            }
        }
    }
}

@Composable
fun ActionCard(
    name: String,
    icon: ImageVector?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Gray.copy(alpha = 0.2f), // Полупрозрачный цвет для фона
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
        }
    }
}

data class ActionCardData(
    val name: String,
    val icon: ImageVector?,
    val onClick: () -> Unit
)