package com.example.botify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.botify.ui.theme.BotifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BotifyTheme {
                Scaffold(modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()) {
                    innerPadding -> ChatPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}