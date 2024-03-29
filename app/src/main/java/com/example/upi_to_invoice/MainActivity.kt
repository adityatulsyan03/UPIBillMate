package com.example.upi_to_invoice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.upi_to_invoice.ui.theme.UPI_to_INVOICETheme

class MainActivity  : ComponentActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UPI_to_INVOICETheme {
                PostOfficeApp()
            }
        }
    }
}