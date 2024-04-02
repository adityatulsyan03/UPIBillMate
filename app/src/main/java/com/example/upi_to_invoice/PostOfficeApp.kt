package com.example.upi_to_invoice

import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.upi_to_invoice.data.home.HomeViewModel
import com.example.upi_to_invoice.navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.navigator.Screen
import com.example.upi_to_invoice.screens.AboutScreen
import com.example.upi_to_invoice.screens.CreateInvoice
import com.example.upi_to_invoice.screens.LogInScreen
import com.example.upi_to_invoice.screens.SignUpScreen
import com.example.upi_to_invoice.screens.StoredSMS
import com.example.upi_to_invoice.screens.TermsAndConditionsScreen

@Composable
fun PostOfficeApp(homeViewModel: HomeViewModel = viewModel()) {

    homeViewModel.checkForActiveSession()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        if(homeViewModel.isUserLoggedIn.value == true){
            PostOfficeAppRouter.navigateTo(Screen.CreateInvoice)
        }

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
                is Screen.CreateInvoice -> {
                    CreateInvoice()
                }
                is Screen.AboutScreen -> {
                    AboutScreen()
                }
                is Screen.StoredSMS -> {
                    StoredSMS()
                }
            }
        }

    }
}