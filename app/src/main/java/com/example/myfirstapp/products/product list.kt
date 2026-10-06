package com.example.myfirstapp.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.navigations.ROUTE_ADDPRODUCT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun productlistScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(
        // Top Bar
        topBar = {
            TopAppBar(
                title = {
                    Text("Product List")
                },
                colors = topAppBarColors(
                    titleContentColor = Color(0xFF8027F5),
                    containerColor = Color(0xFF27F531)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(ROUTE_ADDPRODUCT) },
                containerColor = Color(0xFF27F531)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add icon",
                    tint = Color.Gray
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text("Product list")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun productlistScreenPreview() {
    productlistScreen()
}
