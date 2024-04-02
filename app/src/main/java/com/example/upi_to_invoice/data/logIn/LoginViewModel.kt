package com.example.upi_to_invoice.data.logIn

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.upi_to_invoice.data.rules.Validator
import com.example.upi_to_invoice.navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.navigator.Screen
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel(){

    private val TAG = LoginViewModel::class.simpleName

    var LoginUIState = mutableStateOf(LoginUIState())

    var allVaildationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    fun onEvent(event: LoginUIEvent){
        when(event){
            is LoginUIEvent.EmailChanged -> {
                LoginUIState.value = LoginUIState.value.copy(
                    email = event.email
                )
            }
            is LoginUIEvent.PasswordChanged -> {
                LoginUIState.value = LoginUIState.value.copy(
                    password = event.password
                )
            }
            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
        validatorLoginUIDataWithRules()
    }

    private fun validatorLoginUIDataWithRules(){
        val emailResult = Validator.validateEmail(
            email = LoginUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = LoginUIState.value.password
        )
        LoginUIState.value = LoginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allVaildationsPassed.value = emailResult.status && passwordResult.status
    }

    private fun login(){

        loginInProgress.value = true
        val email= LoginUIState.value.email
        val password = LoginUIState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                Log.d(TAG,"Inside Login Sucess")
                Log.d(TAG, "isSuccessful = ${it.isSuccessful}")
                if(it.isSuccessful) {
                    loginInProgress.value = false
                    PostOfficeAppRouter.navigateTo(Screen.CreateInvoice)
                }
            }
            .addOnFailureListener{
                Log.d(TAG,"Inside Login Failure")
                Log.d(TAG, "localizedMessage = ${it.localizedMessage}")
                loginInProgress.value = false
            }

    }

}