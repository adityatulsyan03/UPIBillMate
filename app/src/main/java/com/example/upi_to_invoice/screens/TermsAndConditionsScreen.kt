package com.example.upi_to_invoice.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.upi_to_invoice.PostOfficeApp
import com.example.upi_to_invoice.components.HeadingTextField
import com.example.upi_to_invoice.navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.navigator.Screen
import com.example.upi_to_invoice.navigator.SystemBackButtonHandler

@Composable
fun TermsAndConditionsScreen() {
    Surface(
    modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp)
    )
    {
        Column(
            modifier = Modifier
                .background(Color.White)
        ) {
            HeadingTextField(
                value = "Term of Use"
            )
        }

        SystemBackButtonHandler {
            PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
        }
    }
}

@Preview
@Composable
fun Preview_TermsAndConditionsScreen() {
    TermsAndConditionsScreen()
}