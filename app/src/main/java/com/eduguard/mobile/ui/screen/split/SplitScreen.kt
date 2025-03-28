package com.eduguard.mobile.ui.screen.split

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduguard.mobile.data.viewmodel.DrawingViewModel
import com.eduguard.mobile.data.viewmodel.PdfViewModel
import com.eduguard.mobile.ui.screen.drawing.DrawingCanvas
import kotlinx.coroutines.launch

@Composable
fun SplitScreen(
    modifier: Modifier = Modifier,
    drawingViewModel: DrawingViewModel = viewModel(),
    pdfViewModel: PdfViewModel = viewModel(),
) {
    // Состояния для разделения экрана
    var splitRatio by remember { mutableStateOf(0.5f) }
    val animatedRatio by animateFloatAsState(
        targetValue = splitRatio,
        animationSpec = tween(100),
        label = "splitRatioAnimation"
    )

    // Состояния для PDF
    var pdfBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Для управления разделителем
    var activeDrag by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    // Контекст и корутин scope
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Обработчик выбора PDF
    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            coroutineScope.launch {
                pdfViewModel.addPdf(selectedUri, context.contentResolver)
            }
        }
    }

    LaunchedEffect(pdfViewModel.activePdf?.currentPageIndex) {
        pdfViewModel.activePdf?.let {
            pdfBitmap = it.loadPage()
        }
    }

    Column(modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f - animatedRatio)
                .background(Color(0xFFE8ECEF))
        ) {
            DrawingCanvas(drawingViewModel)
        }

        val dividerHeight = 16.dp
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dividerHeight)
                .background(if (activeDrag) Color.Blue else Color.Gray)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            activeDrag = true
                        },
                        onDrag = { change, dragAmount ->
                            val newRatio = splitRatio - (dragAmount.y / screenHeightPx)
                            splitRatio = newRatio.coerceIn(0.25f, 0.75f) // Более широкие границы
                        },
                        onDragEnd = {
                            activeDrag = false
                        }
                    )
                }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(animatedRatio)
                .background(Color(0xFFDDE4EE)),
            contentAlignment = Alignment.Center
        ) {
            if (pdfBitmap != null) {
                Image(
                    bitmap = pdfBitmap!!.asImageBitmap(),
                    contentDescription = "PDF Page",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(
                    "Добавить PDF",
                    modifier = Modifier.clickable { pdfLauncher.launch("application/pdf") }
                )
            }
        }
    }
}