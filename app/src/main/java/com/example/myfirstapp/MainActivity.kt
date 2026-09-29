package com.example.myfirstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapp.navigations.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavHost()
            }
        }
    }

@Composable
fun opera() {
   Column(
       modifier= Modifier
       .fillMaxSize()
       .padding(16.dp)
       .background(color = Color.Gray),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center

    ){
       Text("welcome to jetpack compose ", fontSize = 20.sp, color =Color.Magenta, fontStyle = FontStyle.Italic)
       Text("hello this my deli app!", fontSize =26.sp, fontFamily = FontFamily.Cursive, fontStyle = FontStyle.Italic
       )
   }
}
@Preview(showBackground = true)
@Composable
fun operapreview(){
    opera()
}
