package com.eduguard.mobile.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    title: String,
    onBackClick: (() -> Unit)? = null,
    toSplitScreen : (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (onBackClick != null) {
                            IconButton(onClick = onBackClick) {
                                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Назад")
                            }
                        }
                        if (toSplitScreen != null) {
                            IconButton(onClick = toSplitScreen) {
                                Icon(Icons.AutoMirrored.Default.ArrowForward, contentDescription = "К разделенному экрану")
                            }
                        }
                    }
                }
            )
        },
        content = content
    )
}