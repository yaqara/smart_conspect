package com.eduguard.mobile.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eduguard.mobile.data.viewmodel.DrawingViewModel
import com.eduguard.mobile.data.viewmodel.PdfViewModel
import com.eduguard.mobile.ui.screen.authentication.AuthenticationScreen
import com.eduguard.mobile.ui.screen.home.HomeScreen
import com.eduguard.mobile.ui.screen.split_screen.SplitScreen
import com.eduguard.mobile.ui.screen.book.BookScreen
import com.eduguard.mobile.ui.screen.copybook.CopybookScreen
import com.eduguard.mobile.ui.screen.subject_selector.SubjectSelectorScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val pdfVm: PdfViewModel = viewModel()
    val drawingVm: DrawingViewModel = viewModel()

    NavHost(navController = navController, startDestination = "menu") {
        composable("auth") {
            AuthenticationScreen { navController.navigate("menu") }
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("split") {
            SplitScreen(
                navController = navController,
                vm = pdfVm,
                drawingVm = drawingVm
            )
        }
        composable("book") {
            BookScreen(
                navController = navController,
                vm = pdfVm
            )
        }
        composable("copybook") {
            CopybookScreen(
                navController = navController,
                drawingVm = drawingVm
            )
        }
//        composable("menu") {
//            SubjectSelectorScreen(navController)
//        }
    }
}