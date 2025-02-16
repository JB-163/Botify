package com.example.botify

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.botify.ui.theme.BotifyTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        // MainActivity code for handling status bar color and edge to edge.
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Instance for viewModel
        val chatModel : ChatViewModel by viewModels()

        setContent {
            BotifyTheme {
                ChatPage(viewModel = chatModel)
            }
        }
    }
}