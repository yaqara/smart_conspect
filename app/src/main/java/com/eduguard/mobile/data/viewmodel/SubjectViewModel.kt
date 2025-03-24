package com.eduguard.mobile.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.eduguard.mobile.data.model.Subject

class SubjectViewModel : ViewModel() {
    private val _subjects = listOf(
        Subject("1", "Математика", "Научная дисциплина, изучающая числа, величины и их отношения."),
        Subject("2", "Физика", "Научная дисциплина, изучающая природные явления и процессы."),
        Subject("3", "Химия", "Научная дисциплина, изучающая свойства веществ и их преобразования.")
    )
    val subjects: List<Subject> get() = _subjects

    fun onSubjectClick(subject: Subject) {
        Log.d("SUBJECT", "Вы выбрали предмет: ${subject.name}")
    }
}