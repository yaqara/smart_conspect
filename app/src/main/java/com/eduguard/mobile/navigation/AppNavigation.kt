package com.eduguard.mobile.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eduguard.mobile.data.viewmodel.SubjectViewModel
import com.eduguard.mobile.ui.screen.MainScaffold
import com.eduguard.mobile.ui.screen.authentication.AuthenticationScreen
import com.eduguard.mobile.ui.screen.drawing.DrawingScreen
import com.eduguard.mobile.ui.screen.home.HomeScreen
import com.eduguard.mobile.ui.screen.reader.BookScreen
import com.eduguard.mobile.ui.screen.split.SplitScreen
import com.eduguard.mobile.ui.screen.subject_selector.SubjectSelectorScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val subjectViewModel : SubjectViewModel = viewModel()

    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") {
            AuthenticationScreen { navController.navigate("subject_selector") }
        }
        composable("subject_selector") {
            SubjectSelectorScreen(
                viewModel = subjectViewModel,
                navController = navController
            )
        }
        composable("subject/{id}/home") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")!!
            MainScaffold(
                title = subjectViewModel.subjects.find { it.id == id }?.name ?: "Предмет",
                onBackClick = { navController.popBackStack() }
            ) { padding ->
                HomeScreen(
                    navController = navController,
                    subjectId = id,
                    modifier = Modifier.padding(padding)
                )
            }
        }
        composable("subject/{id}/split") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            MainScaffold(
                title = subjectViewModel.subjects.find { it.id == id }?.name ?: "Предмет",
                onBackClick = { navController.popBackStack() }
            ) { padding ->
                SplitScreen(
                    drawingViewModel = subjectViewModel.subjects.find { it.id == id }!!.drawingViewModel,
                    pdfViewModel = subjectViewModel.subjects.find { it.id == id }!!.pdfViewModels,
                    modifier = Modifier.padding(padding)
                )
            }
        }
        composable("subject/{id}/books") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            MainScaffold(
                title = subjectViewModel.subjects.find { it.id == id }?.name ?: "Предмет",
                onBackClick = { navController.popBackStack() },
                toSplitScreen = { navController.navigate("subject/$id/split") }
            ) { padding ->
                BookScreen(
                    pdfViewModel = subjectViewModel.subjects.find { it.id == id }!!.pdfViewModels,
                    modifier = Modifier.padding(padding)
                )
            }
        }
        composable("subject/{id}/note") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id").orEmpty()
            val subject = subjectViewModel.subjects.find { it.id == id }

            if (subject != null) {
                MainScaffold(
                    title = subjectViewModel.subjects.find { it.id == id }?.name ?: "Предмет",
                    onBackClick = { navController.popBackStack() },
                    toSplitScreen = { navController.navigate("subject/$id/split") }
                ) { padding ->
                    DrawingScreen(
                        vm = subject.drawingViewModel,
                        modifier = Modifier.padding(padding)
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Предмет не найден")
                }
            }
        }
    }
}
