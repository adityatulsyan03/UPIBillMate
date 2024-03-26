package com.example.upi_to_invoice

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.upi_to_invoice.Navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.Navigator.Screen
import com.example.upi_to_invoice.Screens.LogInScreen
import com.example.upi_to_invoice.Screens.SignUpScreen
import com.example.upi_to_invoice.Screens.TermsAndConditionsScreen

@Composable
fun PostOfficeApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Crossfade(targetState = PostOfficeAppRouter.currentScreen, label = "") { currentState ->
            when(currentState.value){
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }
                is Screen.TermsAndConditionsScreen -> {
                    TermsAndConditionsScreen()
                }
                is Screen.LogInScreen -> {
                    LogInScreen()
                }
            }
        }

    }
}