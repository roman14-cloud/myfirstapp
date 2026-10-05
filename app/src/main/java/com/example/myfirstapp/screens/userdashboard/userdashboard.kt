
package com.example.myfirstapp.screens.userdashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDashboard(navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("userdashboard") },
                colors = topAppBarColors(
                    containerColor = Color(0xFF27F531),
                    titleContentColor = Color.Green)
            )
        },

        bottomBar = {
            NavigationBar(
                containerColor = Color.Cyan
            ) { NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home icon"
                        )
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Person icon"
                        )
                    },
                    label = {
                        Text("Person")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Cart icon"
                        )
                    },
                    label = {
                        Text("Cart")
                    }
                )
            }
        }

    ) { paddingvalues ->

        Text(
            text = "Welcome to User Dashboard"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun userdashboardpreview() {
    UserDashboard(navController = rememberNavController())

}

