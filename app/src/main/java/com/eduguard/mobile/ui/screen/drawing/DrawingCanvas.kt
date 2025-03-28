package com.eduguard.mobile.ui.screen.drawing

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.eduguard.mobile.data.viewmodel.DrawingViewModel

@Composable
fun DrawingCanvas(
    vm : DrawingViewModel
) {
    var currentPage by remember { mutableStateOf(0) }
    val totalPages = vm.pages.size

    if (currentPage >= totalPages-1) {
        vm.addPage()
    }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            vm.startPath(offset.x, offset.y, currentPage)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            vm.addPoint(change.position.x, change.position.y, currentPage)
                        },
                        onDragEnd = {
                            vm.finishPath(currentPage)
                        }
                    )
                }
        ) {
            val cellSize = 20f
            val gridColor = Color.LightGray

            for (x in 0..(size.width / cellSize).toInt()) {
                drawLine(
                    color = gridColor,
                    start = Offset(x * cellSize, 0f),
                    end = Offset(x * cellSize, size.height),
                    strokeWidth = 1f
                )
            }

            for (y in 0..(size.height / cellSize).toInt()) {
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y * cellSize),
                    end = Offset(size.width, y * cellSize),
                    strokeWidth = 1f
                )
            }

            vm.pages.getOrNull(currentPage)?.forEach { (path, color) ->
                drawPath(path, color, style = Stroke(width = 5f))
            }

            if (vm.currentPoints.isNotEmpty()) {
                val previewPath = Path().apply {
                    vm.currentPoints.forEachIndexed { index, point ->
                        if (index == 0) {
                            moveTo(point.first, point.second)
                        } else {
                            lineTo(point.first, point.second)
                        }
                    }
                }
                drawPath(previewPath, vm.currentColor, style = Stroke(width = 5f))
            }
        }

        BottomMenu(
            currentPage = currentPage,
            totalPages = totalPages,
            onPreviousPage = { currentPage-- },
            onNextPage = {
                if (currentPage < totalPages - 1) {
                    currentPage++
                } else {
                    vm.addPage()
                    currentPage = totalPages
                }
            },
            modifier = Modifier
                .padding(bottom = 30.dp)
                .zIndex(1f)
        )
}

@Composable
fun BottomMenu(
    currentPage: Int,
    totalPages: Int,
    onPreviousPage: () -> Unit,
    onNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color.Transparent) // Прозрачный фон
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Кнопка "Назад" с белым кругом
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { if (currentPage > 0) onPreviousPage() },
                enabled = currentPage > 0,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.Black
                )
            }
        }

        // Текст с номером страницы на белом круге
        Box(
            modifier = Modifier
                .height(30.dp)
                .width(100.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${currentPage + 1}/${totalPages-1}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }

        // Кнопка "Вперед" с белым кругом
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.White, shape = CircleShape)
                .clickable {
                    onNextPage()
                },
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    Log.d("PAGES", "$totalPages $currentPage ${currentPage >= totalPages-1}")
                    onNextPage()
                },
//                enabled = currentPage <= totalPages - 1,
                modifier = Modifier.size(40.dp) // Размер иконки внутри круга
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = "Вперед",
                    tint = Color.Black
                )
            }
        }
    }
}