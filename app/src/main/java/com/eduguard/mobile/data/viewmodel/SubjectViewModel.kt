package com.eduguard.mobile.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.eduguard.mobile.data.model.Subject
import java.util.UUID

class SubjectViewModel : ViewModel() {
    private val _subjects = mutableListOf(
        Subject(UUID.randomUUID().toString(), "Математика", DrawingViewModel(), PdfViewModel()),
        Subject(UUID.randomUUID().toString(), "Физика", DrawingViewModel(), PdfViewModel()),
        Subject(UUID.randomUUID().toString(), "Химия", DrawingViewModel(), PdfViewModel())
    )
    val subjects: List<Subject> get() = _subjects

    fun addSubject(name : String) {
        _subjects.add(
            Subject(
                id = UUID.randomUUID().toString(),
                name = name,
                drawingViewModel = DrawingViewModel(),
                pdfViewModels = PdfViewModel()
            )
        )
    }

    fun onSubjectClick(subject: Subject, navController: NavHostController) {
        navController.navigate("subject/${subject.id}/home")
    }
}