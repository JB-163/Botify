package com.example.botify

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.botify.ui.theme.BotifyTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun ChatPage(modifier: Modifier) {
    AppBar()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    val a = LocalContext.current.applicationContext

    val context = LocalContext.current
    val activity = context as Activity
    val toAppBarColor = MaterialTheme.colorScheme.surfaceVariant
    val view = LocalView.current
    SideEffect{
        val window = activity.window
        val insetsController = WindowInsetsControllerCompat(window,view)
        WindowCompat.getInsetsController(window,view).isAppearanceLightStatusBars = false
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            insetsController.isAppearanceLightStatusBars = false
        }
        window.decorView.setBackgroundColor(toAppBarColor.toArgb())
    }

    TopAppBar(title = { Text("Botify", style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)},
        navigationIcon = {
            IconButton(onClick = { Toast.makeText(a, "Feature not found. Even AI has its lazy days!", Toast.LENGTH_LONG).show()}) {
                Icon(painter = painterResource(R.drawable.nav_icon),
                    contentDescription = null, modifier = Modifier.size(175.dp))
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = toAppBarColor,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ))
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppBarPreview() {
    BotifyTheme {
        AppBar()
    }
}