package com.example.upi_to_invoice

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.upi_to_invoice.Navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.Navigator.Screen
import com.example.upi_to_invoice.Screens.SignUp_Screen

@Composable
fun PostOfficeApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        
        Crossfade(targetState = PostOfficeAppRouter.currentScreen, label = "") { currentState ->
            when(currentState.value){
                is Screen.SignUpScreen -> {
                    Screen.SignUpScreen
                }
                is Screen.TermsAndConditionsScreen -> {
                    Screen.TermsAndConditionsScreen
                }
                is Screen.LogInScreen -> {
                    Screen.LogInScreen
                }
                else -> {}
            }
        }
        
    }
}