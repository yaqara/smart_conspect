package com.eduguard.mobile.ui.screen.split_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduguard.mobile.data.viewmodel.DrawingViewModel

@Composable
fun DrawingScreen(
    vm: DrawingViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var currentPage by remember { mutableStateOf(0) }
    val totalPages = vm.pages.size

    if (currentPage >= vm.pages.size) {
        vm.addPage()
    }

    val menuHeight = 60.dp

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray)
                .height(menuHeight),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { if (currentPage > 0) currentPage-- },
                enabled = currentPage > 0,
                modifier = Modifier.weight(1f)
            ) {
                Text("← Назад")
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Страница ${currentPage + 1} из ${totalPages}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Button(
                onClick = {
                    if (currentPage < totalPages - 1) {
                        currentPage++
                    } else {
                        vm.addPage()
                        currentPage = totalPages
                    }
                },
                enabled = true,
                modifier = Modifier.weight(1f)
            ) {
                Text("Вперед →")
            }
            val hasCurrentPath = vm.currentPoints.isNotEmpty()
            val hasPagePaths = vm.pages.getOrNull(currentPage)?.isNotEmpty() ?: false
            val isUndoEnabled = hasCurrentPath || hasPagePaths

            Button(
                onClick = { vm.undoLastAction(currentPage) },
                enabled = isUndoEnabled,
                modifier = Modifier.weight(1f)
            ) {
                Text("Отменить")
            }
        }

        Canvas(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->//при косание экрана
                            if (offset.y > menuHeight.toPx()) {
                                vm.startPath(offset.x, offset.y - menuHeight.toPx(), currentPage)
                            }
                        },
                        onDrag = { change, _ ->//когда уже ведёшь
                            if (change.position.y > menuHeight.toPx()) {
                                change.consume()
                                vm.addPoint(
                                    change.position.x,
                                    change.position.y - menuHeight.toPx(),
                                    currentPage
                                )
                            }
                        },
                        onDragEnd = {
                            vm.finishPath(currentPage)
                        }
                    )
                }
        ) {

            val gridHeight = size.height - menuHeight.toPx()
            val cellSize = 20f
            val gridColor = Color.LightGray

            for (x in 0..(size.width / cellSize).toInt()) {
                drawLine(
                    color = gridColor,
                    start = Offset(x * cellSize, 0f),
                    end = Offset(x * cellSize, gridHeight),
                    strokeWidth = 1f
                )
            }

            for (y in 0..(gridHeight / cellSize).toInt()) {
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
    }
}

@Composable
private fun Dp.toPx(): Float = this.value * LocalDensity.current.density