package com.example.upi_to_invoice.data

sealed class LoginUIEvent{

    data class EmailChanged(val email:String) : LoginUIEvent()
    data class PasswordChanged(val password:String) : LoginUIEvent()
    object LoginButtonClicked : LoginUIEvent()

}