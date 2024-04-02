package com.example.upi_to_invoice.screens

import android.util.Log
import androidx.compose.runtime.Composable
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun Demo() {
    val db = Firebase.firestore

    db.collection("users")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d("Get The Data", "Your Id: ${document["Your Id"]} \n SMS: ${document["sms"]}" )
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Can't Get The Data", "Error getting documents.", exception)
        }
}