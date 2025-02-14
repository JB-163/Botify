package com.example.botify

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.botify.ui.theme.Constants
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    @SuppressLint("SecretInSource")
    // Instance for Gemini SDK model.
    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = Constants.MODEL_NAME,
        apiKey = Constants.API_KEY
    )

    fun sendMessage(question : String) {
        viewModelScope.launch {
            val chat = generativeModel.startChat()
            val response = chat.sendMessage(question)
            Log.i("Getting Response", response.text.toString())
        }
    }
}