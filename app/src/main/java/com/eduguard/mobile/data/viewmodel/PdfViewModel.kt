package com.eduguard.mobile.data.viewmodel

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.io.IOException

class PdfViewModel : ViewModel() {

    private val _pdfs = mutableListOf<PdfState>()
    val pdfs: List<PdfState> get() = _pdfs

    var activePdfIndex by mutableStateOf(-1)
        private set

    val activePdf: PdfState?
        get() = if (activePdfIndex >= 0 && activePdfIndex < _pdfs.size) _pdfs[activePdfIndex] else null


    suspend fun addPdf(uri: Uri, contentResolver: android.content.ContentResolver) {
        withContext(Dispatchers.IO) {
            try {
                val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
                    ?: throw IOException("Failed to open file descriptor")

                val renderer = PdfRenderer(parcelFileDescriptor)
                val pageCount = renderer.pageCount

                val newPdf = PdfState(
                    uri = uri,
                    renderer = renderer,
                    pageCount = pageCount
                )
                _pdfs.add(newPdf)
                activePdfIndex = _pdfs.size - 1 // Устанавливаем новый PDF как активный
            } catch (e: IOException) {
                e.printStackTrace()
                println("Error adding PDF: ${e.message}")
            }
        }
    }


    fun closePdf(index: Int) {
        if (index in _pdfs.indices) {
            _pdfs[index].closeResources()
            _pdfs.removeAt(index)
            if (activePdfIndex == index) {
                activePdfIndex = if (_pdfs.isNotEmpty()) 0 else -1 // Переключаемся на первый PDF или сбрасываем активный
            } else if (activePdfIndex > index) {
                activePdfIndex-- // Корректируем индекс активного PDF
            }
        }
    }

    fun switchToPdf(index: Int) {
        if (index in _pdfs.indices) {
            activePdfIndex = index
        }
    }

    override fun onCleared() {
        super.onCleared()
        _pdfs.forEach { it.closeResources() }
    }

    data class PdfState(
        val uri: Uri,
        val renderer: PdfRenderer,
        val pageCount: Int,
        var currentPageIndex: Int = 0
    ) {
        fun nextPage() {
            if (currentPageIndex < pageCount - 1) currentPageIndex++
        }

        fun previousPage() {
            if (currentPageIndex > 0) currentPageIndex--
        }

        suspend fun loadPage(): Bitmap? {
            return withContext(Dispatchers.IO) {
                try {
                    val page = renderer.openPage(currentPageIndex)
                    val bitmap = Bitmap.createBitmap(
                        page.width,
                        page.height,
                        Bitmap.Config.ARGB_8888
                    )
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    page.close()
                    bitmap
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }

        fun closeResources() {
            try {
                renderer.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}