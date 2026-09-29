package com.example.myfirstapp.screens.demo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myfirstapp.R

@Composable
fun BoxScreen(){
    Box(modifier = Modifier
        .fillMaxSize()
    ){


Image(
painter=painterResource(id = R.drawable.dog),
contentDescription = "dog",
modifier = Modifier.fillMaxSize(),
contentScale = ContentScale.Crop

)
Text(
text="welcome to my page",
color = Color.Green,
fontSize = 20.sp,
fontFamily = FontFamily.SansSerif,
fontWeight = FontWeight.Bold,
fontStyle = FontStyle.Italic,
modifier = Modifier.align(Alignment.Center)
)}}

@Preview(showBackground = true)
@Composable
fun BoxScreenPreview(){
    BoxScreen()
}