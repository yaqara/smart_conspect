package com.eduguard.mobile.data.model

import com.eduguard.mobile.data.viewmodel.DrawingViewModel
import com.eduguard.mobile.data.viewmodel.PdfViewModel

data class Subject(
    val id: String,
    val name: String,
    val drawingViewModel: DrawingViewModel = DrawingViewModel(),
    val pdfViewModels : PdfViewModel = PdfViewModel()
)