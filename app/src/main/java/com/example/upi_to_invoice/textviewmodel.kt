package com.example.test

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class textviewmodel : ViewModel() {
    private val _line = mutableStateOf("")

    val line: String get() = _line.value

    fun updateline(newLine: String?) {
        _line.value = newLine ?: ""
    }
}