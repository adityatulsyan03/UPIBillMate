package com.example.upi_to_invoice.screens

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.upi_to_invoice.components.NavigationDrawerText
import com.example.upi_to_invoice.components.NavigationItemRow
import com.example.upi_to_invoice.data.NavigationItem
import com.example.upi_to_invoice.data.SMSItem
import com.example.upi_to_invoice.data.home.HomeViewModel
import com.example.upi_to_invoice.navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.navigator.Screen
import com.example.upi_to_invoice.navigator.SystemBackButtonHandler
import com.google.android.play.integrity.internal.i

@Composable
fun StoredSMS() {

    val homeViewModel: HomeViewModel = viewModel()
    
    Surface(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
            .padding(28.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ){
            items(homeViewModel.smsList){
                SMSRow(item = it)
            }
        }
    }

    SystemBackButtonHandler {
        PostOfficeAppRouter.navigateTo(Screen.CreateInvoice)
    }
    
}

@Composable
fun SMSRow(item: SMSItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(18.dp)
            .background(Color.White, shape = RoundedCornerShape(15.dp))
            .padding(18.dp)
    ) {
        Row(){
            Column {
                Text(text = "User Name: ", color = Color.Black, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "UPI SMS: ", color = Color.Black, fontSize = 12.sp)
            }
            Column {
                Text(text = item.FirstId, color = Color.Black, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = item.sms, color = Color.Black, fontSize = 12.sp)
            }
        }
    }
}

@Preview
@Composable
fun PrevSMSRow() {
    SMSRow(item = SMSItem("Aditya","Amt Sent Rs.40.00\n" +
            "From HDFC Bank A/C *9571\n" +
            "To PRASANT KUMAR PRADHA\n" +
            "On 28-03\n" +
            "Ref 408835420969\n" +
            "Not You? Call 18002586161/SMS BLOCK UPI to 7308080808")
    )
}