package com.example.myfirstapp.screens.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.R
import com.example.myfirstapp.navigations.ROUTE_ONBOARDING
import com.example.myfirstapp.ui.theme.yelllogreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(true) {
        delay(3000) //3 seconds delay
        navController.navigate(ROUTE_ONBOARDING)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff0D0C0C))

    ){
        //logo
        Image(
            painter = painterResource(id=R.drawable.nat),
            contentDescription = "nat",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
            )
        //app name
               Text(
                   text = "WELCOME",
                   color=yelllogreen,
                   fontSize = 30.sp)

    }}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    SplashScreen(rememberNavController())
}