package com.example.botify

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Build
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
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ChatPage(viewModel: ChatViewModel) {

    // Variable for handling Keyboard actins.
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            AppBar() // App bar is now inside Scaffold
        },
        content = { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                // Ensures content doesn't overlap with app bar
                .padding(paddingValues)
                .fillMaxSize()
                .imePadding()

                // Code for handling keyboard actions.
                .clickable {
                    focusManager.clearFocus()
                }
            ) {
                MessageList(
                    modifier = Modifier.weight(1f),
                    listOfMessages = viewModel.messageList
                )
                MessageBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding(),
                    onMessageSend = {
                        viewModel.sendMessage(it)
                    },
                    focusManager = focusManager
                )
            }
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    // variable for handling toast message.
    val a = LocalContext.current.applicationContext

    // Code to set status bar color as same of app bar.
    val context = LocalContext.current
    val activity = context as Activity
    val view = LocalView.current
    val toAppBarColor = MaterialTheme.colorScheme.surfaceVariant
    SideEffect {
        val window = activity.window
        val insetsController = WindowInsetsControllerCompat(window, view)
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            insetsController.isAppearanceLightStatusBars = false
        }
        window.decorView.setBackgroundColor(toAppBarColor.toArgb())
    }

    // AppBar code :
    TopAppBar(
        title = {
            Text(
                "Botify", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    Toast.makeText(
                        a,
                        "I was placed here for no reason, just like your tap :)",
                        Toast.LENGTH_LONG
                    ).show()
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null,
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = toAppBarColor,
            titleContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

@Composable
fun MessageBox(
    modifier: Modifier = Modifier,
    onMessageSend: (String) -> Unit,
    focusManager: FocusManager
) {

    val a = LocalContext.current.applicationContext

    var message by rememberSaveable {
        mutableStateOf("")
    }

    // Variable for handling text field focus.
    var isFocused by remember {
        mutableStateOf(false)
    }


    Row(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
            },
            modifier = Modifier
                .weight(1f)
                .padding(3.dp)


                // Code for handling text field focus.
                .onFocusEvent {
                    isFocused = it.isFocused
                }
                .focusTarget(),

            shape = RoundedCornerShape(30.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            ),
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = true,
                capitalization = KeyboardCapitalization.Sentences
            ),
            placeholder = {
                Text(
                    "Ask Botify",
                    color = MaterialTheme.colorScheme.outlineVariant,
                    style = MaterialTheme.typography.labelLarge,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Light
                )
            })

        IconButton(onClick = {
            if (message.isNotEmpty()) {
                onMessageSend(message)
                message = ""

                // Code for handling keyboard actions.
                focusManager.clearFocus()

            } else {
                Toast.makeText(a, "Message cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
        }
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, listOfMessages: List<MessageModel>) {

    LazyColumn(
        modifier = modifier,
        // reverse scroll order
        reverseLayout = true
    ) {

        // reverse order of messages.
        items(listOfMessages.reversed()) {
            MessageRow(messageModel = it)
        }
    }
}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(
                        if (isModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(
                        start = if (isModel) 8.dp else 72.dp,
                        end = if (isModel) 72.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(32f))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(13.dp)
            ) {
                Text(
                    messageModel.message,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        }
    }

}