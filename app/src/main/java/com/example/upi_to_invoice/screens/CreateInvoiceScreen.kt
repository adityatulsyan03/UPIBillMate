package com.example.upi_to_invoice.screens

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.upi_to_invoice.components.AppToolBar
import com.example.upi_to_invoice.components.ButtonComponent
import com.example.upi_to_invoice.data.SignUpViewModel
import com.example.upi_to_invoice.data.textviewmodel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

    private var price: Double by mutableDoubleStateOf(0.0)
    private var secondId: String by mutableStateOf("")
    private var date: String by mutableStateOf("")
    private var bankName: String by mutableStateOf("")
    private var refNo: String by mutableStateOf("")

@Composable
fun CreateInvoice(
    textviewmodel: textviewmodel = viewModel(),
    activity: ComponentActivity,
    signUpViewModel: SignUpViewModel = viewModel()
) {
    var firstId: String by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppToolBar(
                toolbarTitle = "Home",
                logOutButtonClicked = {
                    signUpViewModel.logout()
                }
            )
        }
    ) {paddingValues ->

        Surface(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (activity.intent?.action) {
                    Intent.ACTION_SEND -> {
                        if ("text/plain" == activity.intent.type) {
                            HandleSendText(textviewmodel, activity.intent) // Handle text being sent
                        }
                    }

                    else -> {
                        //Scaffolding
                    }
                }
                TextField(
                    value = firstId,
                    onValueChange = { newid -> firstId = newid },
                    placeholder = { Text("Enter Your Name or ID") }
                )
                Button(onClick = {
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
                    firstId = ""
                }) {
                    Text("Create PDF")
                }
            }
        }

    }

}
    private fun debit(line: String){
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

    private fun createPDF(price: Double,firstId: String,secondId: String,date: String,bankName: String,refNo: String,type: String){
        val pdf = PdfDocument()
        val mypageInfo1 = PdfDocument.PageInfo.Builder(2480,2489,1).create()
        val mypage1 = pdf.startPage(mypageInfo1)
        val canvas = mypage1.canvas
        val mypaint = Paint()

        mypaint.textSize = 250f
        canvas.drawText("INVOICE",
            1450F, 395F,mypaint)
        val mypaintWhite = Paint().apply {
            color = Color.WHITE
        }

        mypaint.textSize = 100f
        canvas.drawRect(119f,708f,2361f,2094f,mypaint)
        canvas.drawRect(125f,714f,2355f,2088f,mypaintWhite)
        mypaint.strokeWidth = 8f
        canvas.drawLine(123f,906f,2357f,906f,mypaint)
        canvas.drawLine(123f,1104f,2357f,1104f,mypaint)
        canvas.drawLine(123f,1302f,2357f,1302f,mypaint)
        canvas.drawLine(123f,1500f,2357f,1500f,mypaint)
        canvas.drawLine(123f,1698f,2357f,1698f,mypaint)
        canvas.drawLine(123f,1896f,2357f,1896f,mypaint)
        mypaint.style = Paint.Style.FILL
        canvas.drawText("From: $firstId",
            200F, 835F,mypaint)
        canvas.drawText("To: $secondId",
            200F, 1033F,mypaint)
        canvas.drawText("Type: $type",
            200F, 1231F,mypaint)
        canvas.drawText("Bank Name: $bankName",
            200F, 1429F,mypaint)
        canvas.drawText("Amount: $price",
            200F, 1627F,mypaint)
        canvas.drawText("Date: $date",
            200F, 1825F,mypaint)
        canvas.drawText("Ref No.: $refNo",
            200F, 2023F,mypaint)
        mypaint.textSize = 80f
        val sdf1 = SimpleDateFormat("yy-MM-dd", Locale.getDefault())
        val currentData: String = sdf1.format(Date())
        canvas.drawText("Date: $currentData",
            70F,120F,mypaint)
        pdf.finishPage(mypage1)

        val sdf = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault())
        val currentDataAndTime: String = sdf.format(Date())
        val fileName = "document_$currentDataAndTime.pdf"
        val file =
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        // Log the file path
        Log.d("FilePath", file.absolutePath)
        pdf.writeTo(file.outputStream())
        pdf.close()
    }
    @Composable
    private fun HandleSendText(textviewmodel: textviewmodel = viewModel(), intent: Intent) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        sharedText?.let {
            textviewmodel.updateline(it)
        }
    }

@Preview(showBackground = true)
@Composable
fun Prev_CreateInvoice() {
    CreateInvoice(activity = ComponentActivity())
}