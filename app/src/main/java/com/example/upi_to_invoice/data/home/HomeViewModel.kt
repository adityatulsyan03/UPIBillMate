package com.example.upi_to_invoice.data.home

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.upi_to_invoice.data.NavigationItem
import com.example.upi_to_invoice.data.signUp.SignUpViewModel
import com.example.upi_to_invoice.navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.navigator.Screen
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {

    private val TAG = SignUpViewModel::class.simpleName

    val navigationItemsList = listOf<NavigationItem>(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            description = "Home Screen",
            itemId = "homeScreen"
        ),
        NavigationItem(
            title = "About",
            icon = Icons.Filled.Person,
            description = "About Screen",
            itemId = "aboutScreen"
        )
    )

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

        val authStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                Log.d(TAG, "Inside sign out success")
                PostOfficeAppRouter.navigateTo(Screen.LogInScreen)
            } else {
                Log.d(TAG, "Inside sign out is not completed")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun checkForActiveSession(){
        if(FirebaseAuth.getInstance().currentUser != null) {
            Log.d(TAG,"Valid Session")
            isUserLoggedIn.value = true
        }else{
            Log.d(TAG,"User is not logged in")
            isUserLoggedIn.value = false
        }
    }

    val emailID: MutableLiveData<String> = MutableLiveData()

    fun getUserData(){
        FirebaseAuth.getInstance().currentUser?.also {
            it.email?.also {email->
                emailID.value = email
            }
        }
    }
}
