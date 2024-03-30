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
import androidx.compose.material.icons.filled.Person
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
import com.example.upi_to_invoice.components.CheckboxCotent
import com.example.upi_to_invoice.components.ClickableLoginTextComponent
import com.example.upi_to_invoice.components.DividerTextComponent
import com.example.upi_to_invoice.components.HeadingTextField
import com.example.upi_to_invoice.components.MyTextField
import com.example.upi_to_invoice.components.NormalTextField
import com.example.upi_to_invoice.components.PasswordTextField
import com.example.upi_to_invoice.data.signUp.SignUpViewModel
import com.example.upi_to_invoice.data.signUp.SignUpUIEvent
import com.example.upi_to_invoice.navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.navigator.Screen

@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel = viewModel() ) {

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ){
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(20.dp)
                    .fillMaxSize()
            ) {

                NormalTextField(
                    value = "Hey There,"
                )

                Spacer(modifier = Modifier.height(10.dp))

                HeadingTextField(
                    value = "Create An Account"
                )

                Spacer(modifier = Modifier.height(30.dp))

                MyTextField(
                    labelValue = "First Name",
                    Icons.Filled.Person,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.FirstNameChanged(it))
                    },
                    errorStatus = signUpViewModel.registrationUIState.value.firstNameError
                )

                MyTextField(
                    labelValue = "Second Name",
                    Icons.Filled.Person,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.LastNameChanged(it))
                    },
                    errorStatus = signUpViewModel.registrationUIState.value.lastNameError
                )

                MyTextField(
                    labelValue = "Email Id",
                    Icons.Filled.Email,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.EmailChanged(it))
                    },
                    errorStatus = signUpViewModel.registrationUIState.value.emailError
                )

                PasswordTextField(
                    labelValue = "Password",
                    Icons.Filled.Lock,
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUIEvent.PasswordChanged(it))
                    },
                    errorStatus = signUpViewModel.registrationUIState.value.passwordError
                )

                Spacer(modifier = Modifier.height(10.dp))

                CheckboxCotent(
                    value = "terms_and_conditions",
                    onTextSelected = {
                        PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
                    },
                    onCheckedChange = {
                        signUpViewModel.onEvent(SignUpUIEvent.PrivacyPolicyCheckedBoxClicked(it))
                    }
                )

                Spacer(modifier = Modifier.height(80.dp))

                ButtonComponent(
                    value = "Register",
                    onButtonClicked = {
                        signUpViewModel.onEvent(SignUpUIEvent.RegisterButtonClicked)
                    },
                    isEnabled = signUpViewModel.allValidationPassed.value
                )

                Spacer(modifier = Modifier.height(5.dp))

                DividerTextComponent()

                Spacer(modifier = Modifier.height(10.dp))

                ClickableLoginTextComponent(
                    tryingToLogin = true,
                    onTextSelected = {
                        PostOfficeAppRouter.navigateTo(Screen.LogInScreen)
                    }
                )

            }
        }
        if(signUpViewModel.SignUpInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Prev_sign() {
    SignUpScreen()
}