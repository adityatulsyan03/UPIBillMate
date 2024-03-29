package com.example.upi_to_invoice.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class textviewmodel : ViewModel() {
    private val _line = mutableStateOf("")

    val line: String get() = _line.value

    fun updateline(newLine: String?) {
        _line.value = newLine ?: ""
    }
}