package com.example.botify

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    // A message list that stores the messages to be displayed.
    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    @SuppressLint("SecretInSource")
    // Instance for Gemini SDK model.
    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = Constants.MODEL_NAME,
        apiKey = Constants.API_KEY
    )

    fun sendMessage(question : String) {
        viewModelScope.launch {
            val chat = generativeModel.startChat()

            // Adding user message to the messageList
            messageList.add(MessageModel(message = question, role = "user"))
            val response = chat.sendMessage(question)
            // Adding model response to the messageList
            messageList.add(MessageModel(message = response.text.toString(),role = "model"))
        }
    }
}