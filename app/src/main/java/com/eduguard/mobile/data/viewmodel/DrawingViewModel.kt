package com.eduguard.mobile.data.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.ViewModel
import com.eduguard.mobile.data.model.DrawPath

class DrawingViewModel : ViewModel() {
    private val _pages = mutableStateListOf<MutableList<DrawPath>>()
    val pages: List<List<DrawPath>> get() = _pages

    private var _currentPath = mutableStateOf(Path())
    val currentPath: Path get() = _currentPath.value

    private var _currentColor = mutableStateOf(Color.Black)
    val currentColor: Color get() = _currentColor.value

    private var _currentThickness = mutableStateOf(5f)
    val currentThickness: Float get() = _currentThickness.value

    private var _currentPoints = mutableStateListOf<Pair<Float, Float>>()
    val currentPoints: List<Pair<Float, Float>> get() = _currentPoints

    fun addPage() {
        _pages.add(mutableStateListOf())
    }

    fun startPath(x: Float, y: Float, page: Int) {
        _currentPath.value = Path().apply { moveTo(x, y) }
        _currentPoints.clear()
        _currentPoints.add(Pair(x, y))
    }

    fun addPoint(x: Float, y: Float, page: Int) {
        _currentPoints.add(Pair(x, y))
    }

    fun finishPath(page: Int) {
        _currentPoints.forEachIndexed { index, point ->
            if (index == 0) {
                _currentPath.value.moveTo(point.first, point.second)
            } else {
                _currentPath.value.lineTo(point.first, point.second)
            }
        }
        if (_pages.size > page) {
            _pages[page].add(DrawPath(_currentPath.value, _currentColor.value, _currentThickness.value))
        }
        _currentPath.value = Path()
        _currentPoints.clear()
    }

    fun setCurrentColor(color: Color) {
        _currentColor.value = color
    }

    fun setCurrentThickness(thickness: Float) {
        _currentThickness.value = thickness
    }

    fun undoLastAction(page: Int) {
        if (_currentPoints.isNotEmpty()) {
            // Отмена текущего незавершенного пути
            _currentPath.value = Path() // Сброс текущего пути
            _currentPoints.clear()      // Очистка точек текущего пути
        } else if (_pages.size > page && _pages[page].isNotEmpty()) {
            // Удаление последнего завершенного пути на странице
            _pages[page].removeAt(_pages[page].lastIndex)
        }
    }


    fun clearPage(page: Int) {
        if (_pages.size > page) {
            _pages[page].clear()
        }
    }


}