package com.example.botify

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
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
        val chatModel = ViewModelProvider(this)[ChatViewModel::class.java]

        setContent {
            BotifyTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = { AppBar() }) { innerPadding ->
                    ChatPage(modifier = Modifier.padding(innerPadding), viewModel = chatModel)
                }
            }
        }
    }
}