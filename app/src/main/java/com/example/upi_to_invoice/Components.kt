package com.example.upi_to_invoice

import android.content.Intent
import android.graphics.drawable.Icon
import android.util.Log
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.K
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NormalTextField(value: String) {
    Text(
        text = value,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 20.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Default
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextField(value: String) {
    Text(
        text = value,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 30.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default
        ),
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(labelValue: String, icon: ImageVector) {

    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue, color = Color.Black) },
        keyboardOptions = KeyboardOptions.Default,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "null"
            )
        }
    )
}

@Composable
fun PasswordTextField(labelValue: String, icon: ImageVector) {

    val password = remember { mutableStateOf("") }

    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue,color = Color.Black) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = password.value,
        onValueChange = {
            password.value = it
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "null"
            )
        },
        trailingIcon = {

            val iconImage = if(passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }

            val description = if(passwordVisible.value){
                "Hide Password"
            }else{
                "Show Password"
            }

            IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
            }) {
                Icon(
                    imageVector = iconImage,
                    contentDescription = description
                )
            }
            
        },
        visualTransformation = if(passwordVisible.value)
            VisualTransformation.None
        else
            PasswordVisualTransformation()
    )
}

@Composable
fun CheckboxCotent(value: String, onTextSelected: (String)->Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(56.dp)
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        val checkedState = remember { mutableStateOf(false) }

        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
            }
        )
        
        ClickableTextComponent(value = value, onTextSelected)

    }
}

@Composable
fun ClickableTextComponent(value: String, onTextSelected: (String)->Unit) {

    val initalText = "By Continuing you accept our "
    val privatePolicyText = "Privacy Policy"
    val ansTest = " and "
    val termsAndConditionsText = "Term of Use"

    val annotatedString = buildAnnotatedString {
        append(initalText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
            pushStringAnnotation(tag = privatePolicyText, annotation = privatePolicyText)
            append(privatePolicyText)
        }
        append(ansTest)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }
    }

    ClickableText(text = annotatedString, onClick = {offset ->
        annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also { span ->
                Log.d("ClickableTextComponent","{$span}")

                if(span.item == termsAndConditionsText || span.item == privatePolicyText){
                    onTextSelected(span.item)
                }
            }
    })

}

@Composable
fun ButtonComponent(value: String) {
    Button(onClick = {

    },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.primary
                        )
                    ),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DividerTextComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = "or",
            fontSize = 18.sp,
            color = Color.Black
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}

@Composable
fun ClickableLoginTextComponent(value1: String,value2: String,onTextSelected: (String)->Unit) {

    val initalText = value1
    val linkText = value2

    val annotatedString = buildAnnotatedString {
        append(initalText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
            pushStringAnnotation(tag = linkText, annotation = linkText)
            append(linkText)
        }
    }

    ClickableText(
        text = annotatedString,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 20.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Default,
            textAlign = TextAlign.Center
        ),
        onClick = {offset ->
        annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also { span ->
                Log.d("ClickableTextComponent","{$span}")

                if(span.item == linkText){
                    onTextSelected(span.item)
                }
            }
    })
}
