package com.example.botify

import android.util.Log
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    fun sendMessage(question : String) {
        Log.i("ChatViewModel", question)
    }
}