package com.eduguard.mobile.ui.screen.split_screen


import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.eduguard.mobile.data.viewmodel.DrawingViewModel
import com.eduguard.mobile.data.viewmodel.PdfViewModel
import com.eduguard.mobile.ui.screen.book.FilePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SplitScreen(
    navController: NavHostController,
    vm: PdfViewModel,
    drawingVm: DrawingViewModel
) {
    var splitRatio by remember { mutableStateOf(0.5f) }
    var isVertical by remember { mutableStateOf(false) }
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val screenHeight = config.screenHeightDp.dp

    var pdfBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(vm.activePdf?.uri, vm.activePdf?.currentPageIndex) {
        vm.activePdf?.let { pdf ->
            pdfBitmap = pdf.loadPage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { navController.navigate("home") }) {
                Text(text = "Назад")
            }
            Button(
                onClick = { isVertical = !isVertical },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
            ) {
                Text(if (isVertical) "Гориз." else "Верт.", color = Color.White)
            }
        }

        if (vm.activePdf == null) {
            FilePicker { uri ->
                coroutineScope.launch {
                    vm.addPdf(uri, context.contentResolver)
                }
            }
        } else {
            if (isVertical) {
                Row(Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .weight(splitRatio)
                            .fillMaxHeight()
                            .background(Color(0xFFDDE4EE)),
                        contentAlignment = Alignment.Center
                    ) {
                        pdfBitmap?.let { bitmap ->
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "PDF Page",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .width(8.dp)
                            .fillMaxHeight()
                            .background(Color(0xFFCCCCCC))
                            .pointerInput(Unit) {
                                detectDragGestures { _, dragAmount ->
                                    splitRatio = (splitRatio + dragAmount.x / screenWidth.value)
                                        .coerceIn(0.2f, 0.8f)
                                }
                            }
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f - splitRatio)
                            .fillMaxHeight()
                            .background(Color(0xFFE8ECEF)),
                        contentAlignment = Alignment.Center
                    ) {
                        DrawingScreen(drawingVm)
                    }
                }
            } else {
                Column(Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(splitRatio)
                            .background(Color(0xFFDDE4EE)),
                        contentAlignment = Alignment.Center
                    ) {
                        pdfBitmap?.let { bitmap ->
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "PDF Page",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(Color(0xFFCCCCCC))
                            .pointerInput(Unit) {
                                detectDragGestures { _, dragAmount ->
                                    splitRatio = (splitRatio + dragAmount.y / screenHeight.value)
                                        .coerceIn(0.2f, 0.8f)
                                }
                            }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f - splitRatio)
                            .background(Color(0xFFE8ECEF)),
                        contentAlignment = Alignment.Center
                    ) {
                        DrawingScreen(drawingVm)
                    }
                }
            }
        }
    }
}