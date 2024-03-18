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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.upi_to_invoice.ui.theme.UPI_to_INVOICETheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import kotlin.math.sign

class SignUp_Page : ComponentActivity()  {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UPI_to_INVOICETheme{
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Sign(activity = this)
                }
            }
        }
    }
}

@Composable
fun Sign(activity: ComponentActivity) {
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
                Intent(activity.applicationContext,TermsAndConditions_Page::class.java).also {
                    activity.startActivity(it)
                }
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
            value1 = "Already have an account?",
            value2 = "Login",
            onTextSelected = {
                Intent(activity.applicationContext, Login_Page::class.java).also {
                    activity.startActivity(it)
                }
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun prev_sign() {
    Sign(activity = ComponentActivity())
}