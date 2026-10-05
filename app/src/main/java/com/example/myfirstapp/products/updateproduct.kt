package com.example.myfirstapp.products

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun updateproductScreen(navController: NavHostController = rememberNavController()) {

}

@Preview(showBackground = true)
@Composable
fun updateproductScreenPreview() {
    updateproductScreen()
}
