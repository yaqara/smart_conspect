package com.eduguard.mobile.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

data class DrawPath(
    val path:Path,
    var color: Color,
    var thickness: Float
)
