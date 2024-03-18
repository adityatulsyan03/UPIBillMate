package com.example.upi_to_invoice

import android.content.Intent
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
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.upi_to_invoice.ui.theme.UPI_to_INVOICETheme

class Login_Page : ComponentActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UPI_to_INVOICETheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    login(activity = this)
                }
            }
        }
    }
}

@Composable
fun login(activity: ComponentActivity) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ){

        NormalTextField(
            value = "Hey There,"
        )

        Spacer(modifier = Modifier.height(10.dp))

        HeadingTextField(
            value = "Welcome Back"
        )

        Spacer(modifier = Modifier.height(30.dp))

        MyTextField(
            labelValue = "UserName",
            Icons.Filled.Person
        )

        PasswordTextField(
            labelValue = "Password",
            icon = Icons.Filled.Lock
        )

        Spacer(modifier = Modifier.height(10.dp))

        CheckboxCotent(
            value = "terms_and_conditions",
            onTextSelected = {
                Intent(activity.applicationContext,TermsAndConditions_Page::class.java).also {
                    activity.startActivity(it)
                }
            }
        )

        Spacer(modifier = Modifier.height(80.dp))

        ButtonComponent(
            value = "Login"
        )

        Spacer(modifier = Modifier.height(5.dp))

        DividerTextComponent()

        Spacer(modifier = Modifier.height(10.dp))

        ClickableLoginTextComponent(
            value1 = "Don't have an account yet?",
            value2 = "Register",
            onTextSelected = {
                Intent(activity.applicationContext, SignUp_Page::class.java).also {
                    activity.startActivity(it)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun prev_login() {
    login(activity = ComponentActivity())
}