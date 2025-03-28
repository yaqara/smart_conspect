package com.eduguard.mobile.ui.screen.reader

 import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduguard.mobile.data.viewmodel.PdfViewModel
import kotlinx.coroutines.launch

@Composable
fun FilePicker(onFileSelected: (Uri) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onFileSelected(uri)
        } else {
            println("File selection canceled")
        }
    }

    Button(
        onClick = {

            launcher.launch("application/pdf")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Выбрать PDF файл")
    }
}

@Composable
fun BookScreen(
    pdfViewModel: PdfViewModel = viewModel(),
    modifier : Modifier
) {
    var scale by remember { mutableStateOf(1f) }
    val context = LocalContext.current
    var currentPageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val currentPdf = pdfViewModel.activePdf
    val totalPages = currentPdf?.pageCount ?: 0
    val showNavigation = totalPages > 1

    LaunchedEffect(pdfViewModel.activePdf?.uri, pdfViewModel.activePdf?.currentPageIndex) {
        pdfViewModel.activePdf?.let { pdf ->
            currentPageBitmap = pdf.loadPage()
        }
    }

    var showFilePicker by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showNavigation) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp, bottom = 100.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            pdfViewModel.activePdf?.previousPage()
                            coroutineScope.launch {
                                currentPageBitmap = pdfViewModel.activePdf?.loadPage()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Previous Page"
                        )
                    }
                    IconButton(
                        onClick = {
                            pdfViewModel.activePdf?.nextPage()
                            coroutineScope.launch {
                                currentPageBitmap = pdfViewModel.activePdf?.loadPage()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next Page"
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(pdfViewModel.pdfs) { index, pdf ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { pdfViewModel.switchToPdf(index) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "PDF ${index + 1}", maxLines = 1)
                            IconButton(onClick = { pdfViewModel.closePdf(index) }) {
                                Icon(Icons.Default.Close, contentDescription = "Close PDF")
                            }
                        }
                    }
                }
            }

            if (pdfViewModel.pdfs.isEmpty()) {
                FilePicker { uri ->
                    coroutineScope.launch {
                        pdfViewModel.addPdf(uri, context.contentResolver)
                    }
                }
            } else {
                if (showFilePicker) {
                    FilePicker { uri ->
                        coroutineScope.launch {
                            pdfViewModel.addPdf(uri, context.contentResolver)
                        }
                        showFilePicker = false
                    }
                }

                Button(
                    onClick = { showFilePicker = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Добавить PDF")
                }

                if (currentPageBitmap == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTransformGestures { _, _, zoom, _ ->
                                    scale *= zoom
                                }
                            }
                    ) {
                        currentPageBitmap?.let { bitmap ->
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "PDF Page",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .scale(scale)
                            )
                        }
                    }
                }
            }
        }
    }
}