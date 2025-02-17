package com.example.botify

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    // A message list that stores the messages to be displayed.
    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    @SuppressLint("SecretInSource")
    // Instance for Gemini SDK model.
    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = Constants.MODEL_NAME,
        apiKey = Constants.API_KEY
    )

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    // Code for saving chat history to API
                    history = messageList.map {
                        content(it.role) {
                            text(it.message)
                        }
                    }.toList()
                )

                // Adding user message to the messageList
                messageList.add(MessageModel(message = question, role = "user"))

                // Fake message added to show typing
                messageList.add(MessageModel("Typing...", role = "model"))
                val response = chat.sendMessage(question)
                // Removing fake message
                messageList.removeLast()

                // Adding model response to the messageList
                messageList.add(MessageModel(message = response.text.toString(), role = "model"))
            }
            catch (e : Exception) {
                messageList.removeLast()
                messageList.add(MessageModel("Error : ${e.message.toString()}", role = "model"))
            }
        }
    }
}