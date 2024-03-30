package com.example.upi_to_invoice.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.upi_to_invoice.components.AppToolBar
import com.example.upi_to_invoice.components.HeadingTextField
import com.example.upi_to_invoice.components.NavigationDrawerBody
import com.example.upi_to_invoice.components.NavigationDrawerHeader
import com.example.upi_to_invoice.components.NormalTextField
import com.example.upi_to_invoice.data.home.HomeViewModel
import com.example.upi_to_invoice.navigator.PostOfficeAppRouter
import com.example.upi_to_invoice.navigator.Screen
import com.example.upi_to_invoice.ui.theme.UPI_to_INVOICETheme
import kotlinx.coroutines.launch

@Composable
fun AboutScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    UPI_to_INVOICETheme {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        homeViewModel.getUserData()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color.White)
                        .fillMaxWidth(0.85f)
                ) {
                    NavigationDrawerHeader(homeViewModel.emailID.value)
                    NavigationDrawerBody(NavigationDrawerItems = homeViewModel.navigationItemsList,
                        onNavigationItemClicked = { selectedItem ->
                            when (selectedItem.itemId) {
                                "homeScreen" -> PostOfficeAppRouter.navigateTo(Screen.CreateInvoice)
                                "aboutScreen" -> PostOfficeAppRouter.navigateTo(Screen.AboutScreen)
                            }
                        })
                }
            },
            gesturesEnabled = drawerState.isOpen
        ) {
            Scaffold(
                topBar = {
                    AppToolBar(
                        toolbarTitle = "About Me",
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
                        Text(
                            text = "About Me",
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}