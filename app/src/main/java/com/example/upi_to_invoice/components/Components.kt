package com.example.upi_to_invoice.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.upi_to_invoice.data.NavigationItem
import com.example.upi_to_invoice.data.SMSItem
import com.example.upi_to_invoice.data.home.HomeViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

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
fun MyTextField(labelValue: String, icon: ImageVector,
                onTextSelected: (String) -> Unit,
                errorStatus: Boolean = false
) {

    val textValue = remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it;
            onTextSelected(it)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "null"
            )
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(labelValue: String, icon: ImageVector,
                      onTextSelected: (String) -> Unit,
                      errorStatus: Boolean = false
) {

    val localFocusManager = LocalFocusManager.current

    val password = remember { mutableStateOf("") }

    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 1,
        keyboardActions = KeyboardActions{
            localFocusManager.clearFocus()
        },
        value = password.value,
        onValueChange = {
            password.value = it;
            onTextSelected(it)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
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
            PasswordVisualTransformation(),
        isError = !errorStatus
    )
}

@Composable
fun CheckboxCotent(value: String, onTextSelected: (String)->Unit, onCheckedChange : (Boolean) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        val checkedState = remember { mutableStateOf(false) }

        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
                onCheckedChange.invoke(it)
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
fun ButtonComponent(value: String, onButtonClicked: () -> Unit, isEnabled : Boolean = false) {
    Button(
        onClick = {
            onButtonClicked.invoke()
    },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        enabled = isEnabled
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
fun ClickableLoginTextComponent(tryingToLogin: Boolean = true,onTextSelected: (String)->Unit) {

    val initalText = if(tryingToLogin) "Already have an account? " else "Don't have an account yet? "
    val LoginText = if(tryingToLogin) "Login" else "Register"

    val annotatedString = buildAnnotatedString {
        append(initalText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
            pushStringAnnotation(tag = LoginText, annotation = LoginText)
            append(LoginText)
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

                if(span.item == LoginText){
                    onTextSelected(span.item)
                }
            }
    })
}

@Composable
fun UnderLinedNormalTextField(value: String) {
    Text(
        text = value,
        color = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 20.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Default
        ),
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBar(toolbarTitle:String,logOutButtonClicked : ()-> Unit,
               navigationIconClicked : ()-> Unit) {
    
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
                modifier = Modifier.padding(20.dp,0.dp),
                text = toolbarTitle,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navigationIconClicked.invoke()
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {
                logOutButtonClicked.invoke()
            }) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "LogOut",
                    tint = Color.White,
                )
            }
        }
    )
}

@Composable
fun NavigationDrawerHeader(value: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .background(MaterialTheme.colorScheme.primary)
            .padding(32.dp),
        contentAlignment = Alignment.TopStart
    ){
        
        NavigationDrawerText(
            title = value?:"App Drawer",
            textUnit = 24.sp
        )
        
    }
}

@Composable
fun NavigationDrawerBody(NavigationDrawerItems: List<NavigationItem>,
                         onNavigationItemClicked: (NavigationItem) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ){

        items(NavigationDrawerItems){
            NavigationItemRow(item = it, onNavigationItemClicked)
        }

    }
}

@Composable
fun NavigationItemRow(item: NavigationItem,
                      onNavigationItemClicked: (NavigationItem) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigationItemClicked.invoke(item)
            }
            .padding(16.dp)
    ){

        Icon(
            imageVector = item.icon,
            contentDescription = item.description
        )

        Spacer(modifier = Modifier.width(18.dp))
        
        NavigationDrawerText(title = item.title, 18.sp)

    }
}

@Composable
fun NavigationDrawerText(title: String, textUnit: TextUnit) {

    val shadowOffset = Offset(2f,4f)
    
    Text(
        text = title,
        style = TextStyle(
            color = Color.Black,
            fontSize = textUnit,
            fontStyle = FontStyle.Normal,
            shadow = Shadow(
                color = MaterialTheme.colorScheme.primary,
                offset = shadowOffset, 2f
            )
        )
    )
}