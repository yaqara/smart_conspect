package com.eduguard.mobile.ui.screen.book

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eduguard.mobile.data.viewmodel.PdfViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun FilePicker(onFileSelected: (Uri) -> Unit) {
    val context = LocalContext.current
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
fun BookScreen(navController: NavHostController, vm: PdfViewModel) {
    var scale by remember { mutableStateOf(1f) }
    val context = LocalContext.current
    var currentPageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(vm.activePdf?.uri, vm.activePdf?.currentPageIndex) {
        vm.activePdf?.let { pdf ->
            currentPageBitmap = pdf.loadPage()
        }
    }

    var showFilePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(modifier = Modifier.padding(8.dp)) {
                Button(
                    onClick = { navController.navigate("split") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Раздельный экран")
                }
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Домой")
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp, bottom = 100.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        vm.activePdf?.previousPage()
                        coroutineScope.launch {
                            currentPageBitmap = vm.activePdf?.loadPage()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous Page")
                }
                IconButton(
                    onClick = {
                        vm.activePdf?.nextPage()
                        coroutineScope.launch {
                            currentPageBitmap = vm.activePdf?.loadPage()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next Page")
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
                itemsIndexed(vm.pdfs) { index, pdf ->
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { vm.switchToPdf(index) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "PDF ${index + 1}", maxLines = 1)
                            IconButton(onClick = { vm.closePdf(index) }) {
                                Icon(Icons.Default.Close, contentDescription = "Close PDF")
                            }
                        }
                    }
                }
            }

            if (vm.pdfs.isEmpty()) {
                FilePicker { uri ->
                    coroutineScope.launch {
                        vm.addPdf(uri, context.contentResolver)
                    }
                }
            } else {
                if (showFilePicker) {
                    FilePicker { uri ->
                        coroutineScope.launch {
                            vm.addPdf(uri, context.contentResolver)
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