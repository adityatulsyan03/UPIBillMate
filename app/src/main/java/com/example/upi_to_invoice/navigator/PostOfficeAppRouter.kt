package com.example.upi_to_invoice.navigator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen(){

    object  SignUpScreen : Screen()
    object  TermsAndConditionsScreen : Screen()
    object LogInScreen : Screen()
    object CreateInvoice : Screen()
    object AboutScreen : Screen()
    object StoredSMS : Screen()
}

object PostOfficeAppRouter {

    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignUpScreen)

    fun navigateTo(destination : Screen){
        currentScreen.value = destination
    }

}