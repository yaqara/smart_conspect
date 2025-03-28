package com.eduguard.mobile.ui.screen.drawing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.eduguard.mobile.data.viewmodel.DrawingViewModel

@Composable
fun DrawingScreen(
    modifier: Modifier = Modifier,
    vm: DrawingViewModel,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        DrawingCanvas(vm = vm)
    }
}