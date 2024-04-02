package com.example.upi_to_invoice.data.home

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Storage
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.upi_to_invoice.data.NavigationItem
import com.example.upi_to_invoice.data.SMSItem
import com.example.upi_to_invoice.data.signUp.SignUpViewModel
import com.example.upi_to_invoice.navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.navigator.Screen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

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
        ),
        NavigationItem(
            title = "Stored SMS",
            icon = Icons.Filled.Storage,
            description = "Stored SMS Screeen",
            itemId = "storedSMSScreen"
        )
    )

    val isUserLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun logout() {
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

    fun checkForActiveSession() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d(TAG, "Valid Session")
            isUserLoggedIn.value = true
        } else {
            Log.d(TAG, "User is not logged in")
            isUserLoggedIn.value = false
        }
    }

    val emailID: MutableLiveData<String> = MutableLiveData()

    fun getUserData() {
        FirebaseAuth.getInstance().currentUser?.also {
            it.email?.also { email ->
                emailID.value = email
            }
        }
    }

    val smsList = arrayListOf<SMSItem>()

    fun getSMS(){
        val db = Firebase.firestore
        db.collection(emailID.value.toString())
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val data = SMSItem(
                        FirstId = document.getString("your_id") ?: "",
                        sms = document.getString("sms") ?: ""
                    )
                    smsList.add(
                        data
                    )
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Can't Get The Data", "Error getting documents.", exception)
            }
    }

}