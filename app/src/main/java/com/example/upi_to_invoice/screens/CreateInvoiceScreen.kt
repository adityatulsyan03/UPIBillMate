package com.example.upi_to_invoice.screens

import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.upi_to_invoice.components.AppToolBar
import com.example.upi_to_invoice.components.NavigationDrawerBody
import com.example.upi_to_invoice.components.NavigationDrawerHeader
import com.example.upi_to_invoice.data.SMSItem
import com.example.upi_to_invoice.data.home.HomeViewModel
import com.example.upi_to_invoice.data.textviewmodel
import com.example.upi_to_invoice.navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.navigator.Screen
import com.example.upi_to_invoice.navigator.SystemBackButtonHandler
import com.example.upi_to_invoice.ui.theme.UPI_to_INVOICETheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

var price: Double by mutableDoubleStateOf(0.0)
var secondId: String by mutableStateOf("")
var date: String by mutableStateOf("")
var bankName: String by mutableStateOf("")
var refNo: String by mutableStateOf("")

@Composable
fun CreateInvoice(
    textviewmodel: textviewmodel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()
) {
    var firstId: String by remember { mutableStateOf("") }

    var sms: String by remember { mutableStateOf("") }

    val db = Firebase.firestore

    UPI_to_INVOICETheme(){

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        homeViewModel.getUserData()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(androidx.compose.ui.graphics.Color.White)
                        .fillMaxWidth(0.85f)
                ) {
                    NavigationDrawerHeader(homeViewModel.emailID.value)
                    NavigationDrawerBody(NavigationDrawerItems = homeViewModel.navigationItemsList,
                        onNavigationItemClicked = {selectedItem->
                            when (selectedItem.itemId) {
                                "homeScreen" -> PostOfficeAppRouter.navigateTo(Screen.CreateInvoice)
                                "aboutScreen" -> PostOfficeAppRouter.navigateTo(Screen.AboutScreen)
                                "storedSMSScreen" -> PostOfficeAppRouter.navigateTo(Screen.StoredSMS)
                            }
                        })
                }
            },
            gesturesEnabled = drawerState.isOpen
        ) {
            Scaffold(
                topBar = {
                    AppToolBar(
                        toolbarTitle = "Home",
                        logOutButtonClicked = {
                            homeViewModel.logout()
                        },
                        navigationIconClicked = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    )
                }
            ) { paddingValues ->

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            value = firstId,
                            onValueChange = { newid -> firstId = newid },
                            placeholder = { Text("Enter Your Name or ID") },
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextField(
                            value = sms,
                            onValueChange = { newid -> sms = newid },
                            placeholder = { Text("Enter the sms") }
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                        Button(onClick = {
                            textviewmodel.updateline(sms)
                            if (textviewmodel.line.startsWith("Amt")) {
                                debit(textviewmodel.line)
                            } else {
                                credit(textviewmodel.line)
                            }
                            if (textviewmodel.line.startsWith("Amt")) {
                                createPDF(price, firstId, secondId, date, bankName, refNo, "Debit")
                            } else {
                                val temp: String = firstId
                                firstId = secondId
                                secondId = temp
                                createPDF(price, firstId, secondId, date, bankName, refNo, "Credit")
                            }
                            if(firstId != ""){
                                val user = hashMapOf(
                                    "your_id" to firstId,
                                    "sms" to sms
                                )

                                homeViewModel.emailID.value?.let {
                                    db.collection(it)
                                        .add(user)
                                        .addOnSuccessListener { documentReference ->
                                            Log.d(
                                                "saved",
                                                "DocumentSnapshot added with ID: ${documentReference.id}"
                                            )
                                        }
                                        .addOnFailureListener { e ->
                                            Log.d("Can't save", "Error adding document", e)
                                        }
                                }
                            }
                            firstId = ""
                            sms = ""
                        }) {
                            Text("Create PDF")
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = {
                            homeViewModel.getSMS()
                        }) {
                            Text(text = "get data")
                        }
                    }
                }

            }
        }
    }

}

private fun debit(line: String) {
    val priceRegex = Regex("""Amt Sent Rs\.(\d+\.\d{2})""")
    val bankNameRegex = Regex("""From (\w+) Bank""")
    val secondManRegex = Regex("""To (.+)\n""")
    val dateRegex = Regex("""On (\d{2}-\d{2})""")
    val refNoRegex = Regex("""Ref (\d+)""")

    val priceMatchResult = priceRegex.find(line)
    val bankNameMatchResult = bankNameRegex.find(line)
    val secondManMatchResult = secondManRegex.find(line)
    val dateMatchResult = dateRegex.find(line)
    val refNoMatchResult = refNoRegex.find(line)

    price = priceMatchResult?.groups?.get(1)?.value?.toDoubleOrNull() ?: 0.0
    bankName = bankNameMatchResult?.groups?.get(1)?.value ?: ""
    secondId = secondManMatchResult?.groups?.get(1)?.value ?: ""
    date = dateMatchResult?.groups?.get(1)?.value ?: ""
    refNo = refNoMatchResult?.groups?.get(1)?.value ?: ""
}

private fun credit(line: String) {

    val priceRegex = Regex("""Rs\. (\d+\.\d{2})""")
    val secondIdRegex = Regex("""VPA\s+([\w@]+)""")
    val dataRegex = Regex("""(\d{2}-\d{2}-\d{2})""")
    val bankNameRegex = Regex("""(\w+) Bank""")
    val refNoRegex = Regex("""\b(\d{12})\b""")

    val priceMatchResult = priceRegex.find(line)
    val secondIdMatchResult = secondIdRegex.find(line)
    val dataMatchResult = dataRegex.find(line)
    val bankNameMatchResult = bankNameRegex.find(line)
    val refNoMatchResult = refNoRegex.find(line)

    price = priceMatchResult?.groups?.get(1)?.value?.toDoubleOrNull() ?: 0.0
    secondId = secondIdMatchResult?.groups?.get(1)?.value ?: ""
    date = dataMatchResult?.groups?.get(1)?.value ?: ""
    bankName = bankNameMatchResult?.groups?.get(1)?.value ?: ""
    refNo = refNoMatchResult?.groups?.get(1)?.value ?: ""

}

private fun createPDF(
    price: Double,
    firstId: String,
    secondId: String,
    date: String,
    bankName: String,
    refNo: String,
    type: String
) {
    val pdf = PdfDocument()
    try {
        val mypageInfo1 = PdfDocument.PageInfo.Builder(2480, 2489, 1).create()
        val mypage1 = pdf.startPage(mypageInfo1)
        val canvas = mypage1.canvas
        val mypaint = Paint()

        mypaint.textSize = 250f
        canvas.drawText(
            "INVOICE",
            1450F, 395F, mypaint
        )
        val mypaintWhite = Paint().apply {
            color = Color.WHITE
        }

        mypaint.textSize = 100f
        canvas.drawRect(119f, 708f, 2361f, 2094f, mypaint)
        canvas.drawRect(125f, 714f, 2355f, 2088f, mypaintWhite)
        mypaint.strokeWidth = 8f
        canvas.drawLine(123f, 906f, 2357f, 906f, mypaint)
        canvas.drawLine(123f, 1104f, 2357f, 1104f, mypaint)
        canvas.drawLine(123f, 1302f, 2357f, 1302f, mypaint)
        canvas.drawLine(123f, 1500f, 2357f, 1500f, mypaint)
        canvas.drawLine(123f, 1698f, 2357f, 1698f, mypaint)
        canvas.drawLine(123f, 1896f, 2357f, 1896f, mypaint)
        mypaint.style = Paint.Style.FILL
        canvas.drawText(
            "From: $firstId",
            200F, 835F, mypaint
        )
        canvas.drawText(
            "To: $secondId",
            200F, 1033F, mypaint
        )
        canvas.drawText(
            "Type: $type",
            200F, 1231F, mypaint
        )
        canvas.drawText(
            "Bank Name: $bankName",
            200F, 1429F, mypaint
        )
        canvas.drawText(
            "Amount: $price",
            200F, 1627F, mypaint
        )
        canvas.drawText(
            "Date: $date",
            200F, 1825F, mypaint
        )
        canvas.drawText(
            "Ref No.: $refNo",
            200F, 2023F, mypaint
        )
        mypaint.textSize = 80f
        val sdf1 = SimpleDateFormat("yy-MM-dd", Locale.getDefault())
        val currentData: String = sdf1.format(Date())
        canvas.drawText(
            "Date: $currentData",
            70F, 120F, mypaint
        )
        pdf.finishPage(mypage1)

        val folderName = "UPI Invoice"
        val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), folderName)
        if (!folder.exists())
            folder.mkdirs()
        val sdf = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
        val currentDataAndTime: String = sdf.format(Date())
        val fileName = "document_$currentDataAndTime.pdf"
        val file =
            File(
                folder,
                fileName
            )
        Log.d("FilePath", file.absolutePath)
        pdf.writeTo(file.outputStream())
        pdf.close()
    }catch (e: Exception) {
        Log.e("PDFCreationError", "Error: e", e)
    }
}

@Preview(showBackground = true)
@Composable
fun Prev_CreateInvoice() {
    CreateInvoice()
}