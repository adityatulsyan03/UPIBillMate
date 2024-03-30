package com.example.upi_to_invoice.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.upi_to_invoice.components.ButtonComponent
import com.example.upi_to_invoice.components.ClickableLoginTextComponent
import com.example.upi_to_invoice.components.DividerTextComponent
import com.example.upi_to_invoice.components.HeadingTextField
import com.example.upi_to_invoice.components.MyTextField
import com.example.upi_to_invoice.components.NormalTextField
import com.example.upi_to_invoice.components.PasswordTextField
import com.example.upi_to_invoice.components.UnderLinedNormalTextField
import com.example.upi_to_invoice.data.logIn.LoginUIEvent
import com.example.upi_to_invoice.data.logIn.LoginViewModel
import com.example.upi_to_invoice.navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.navigator.Screen
import com.example.upi_to_invoice.navigator.SystemBackButtonHandler

@Composable
fun LogInScreen(loginViewModel: LoginViewModel = viewModel()) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ){
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(20.dp)
            ) {

                NormalTextField(
                    value = "Hey There,"
                )

                Spacer(modifier = Modifier.height(10.dp))

                HeadingTextField(
                    value = "Welcome Back"
                )

                Spacer(modifier = Modifier.height(30.dp))

                MyTextField(
                    labelValue = "Email",
                    Icons.Filled.Email,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = loginViewModel.LoginUIState.value.emailError
                )

                PasswordTextField(
                    labelValue = "Password",
                    icon = Icons.Filled.Lock,
                    onTextSelected = {
                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = loginViewModel.LoginUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(10.dp))

                UnderLinedNormalTextField(
                    value = "Forgot Password?"
                )

                Spacer(modifier = Modifier.height(80.dp))

                ButtonComponent(
                    value = "Login",
                    onButtonClicked = {
                        loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
                    },
                    isEnabled = loginViewModel.allVaildationsPassed.value
                )

                Spacer(modifier = Modifier.height(5.dp))

                DividerTextComponent()

                Spacer(modifier = Modifier.height(10.dp))

                ClickableLoginTextComponent(
                    tryingToLogin = false,
                    onTextSelected = {
                        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
                    }
                )
            }

            if(loginViewModel.loginInProgress.value) {
                CircularProgressIndicator()
            }

        }
        SystemBackButtonHandler {
            PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun prev_Login() {
    LogInScreen()
}