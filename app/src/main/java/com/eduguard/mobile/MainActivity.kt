package com.eduguard.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eduguard.mobile.navigation.AppNavigation
import com.eduguard.mobile.ui.screen.split_screen.DrawingScreen
import com.eduguard.mobile.ui.screen.split_screen.SplitScreen
import com.eduguard.mobile.ui.theme.MobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileTheme {
                AppNavigation()
            }
        }
    }
}