package com.example.upi_to_invoice.Screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.upi_to_invoice.Components.ButtonComponent
import com.example.upi_to_invoice.Components.CheckboxCotent
import com.example.upi_to_invoice.Components.ClickableLoginTextComponent
import com.example.upi_to_invoice.Components.DividerTextComponent
import com.example.upi_to_invoice.Components.HeadingTextField
import com.example.upi_to_invoice.Components.MyTextField
import com.example.upi_to_invoice.Components.NormalTextField
import com.example.upi_to_invoice.Components.PasswordTextField
import com.example.upi_to_invoice.Navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.Navigator.Screen
import com.example.upi_to_invoice.ui.theme.UPI_to_INVOICETheme

class SignUp_Screen : ComponentActivity()  {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UPI_to_INVOICETheme{
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Sign()
                }
            }
        }
    }
}

@Composable
fun Sign() {
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
            Icons.Filled.Person
        )

        MyTextField(
            labelValue = "Second Name",
            Icons.Filled.Person
        )

        MyTextField(
            labelValue = "Email Id",
            Icons.Filled.Email
        )

        PasswordTextField(
            labelValue = "Password",
            Icons.Filled.Lock
        )

        Spacer(modifier = Modifier.height(10.dp))

        CheckboxCotent(
            value = "terms_and_conditions",
            onTextSelected = {
                PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
            }
        )

        Spacer(modifier = Modifier.height(80.dp))

        ButtonComponent(
            value = "Register"
        )

        Spacer(modifier = Modifier.height(5.dp))

        DividerTextComponent()

        Spacer(modifier = Modifier.height(10.dp))

        ClickableLoginTextComponent(
            tryingToLogin = false,
            onTextSelected = {
                PostOfficeAppRouter.navigateTo(Screen.LogInScreen)
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun prev_sign() {
    Sign()
}