package com.example.myfirstapp.screens.demo

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun Intentscreen(navController: NavHostController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Intents",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Open Camera
        Button(onClick = {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            context.startActivity(cameraIntent)
        }) {
            Text("Open Camera")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Open Browser
        Button(onClick = {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
            context.startActivity(browserIntent)
        }) {
            Text("Open Browser")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Open Google Maps
        Button(onClick = {
            val mapIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=my location"))
            context.startActivity(mapIntent)
        }) {
            Text("Open Google Maps")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Open Phone Dial
        Button(onClick = {
            val callIntent =
                Intent(Intent.ACTION_DIAL, Uri.parse("tel:0727951209"))
            context.startActivity(callIntent)
        }) {
            Text("Call")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Open SMS
        Button(onClick = {
            val smsIntent =
                Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:0727951209"))
            smsIntent.putExtra("sms_body", "hello from my intent in my app")
            context.startActivity(smsIntent)
        }) {
            Text("Send SMS")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Open Email
        Button(onClick = {
            val emailIntent =
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:romanian241@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "hello WELCOME ONBOARD")
            context.startActivity(emailIntent)
        }) {
            Text("Send Email")
        }
        //simtoolkit
        Button(onClick = {
            val simToolkitIntent = context.packageManager.getLaunchIntentForPackage("com.android.stk")
            simToolkitIntent?.let { context.startActivity(it) }
        }){Text("stk push")}
    }
}

@Preview(showBackground = true)
@Composable
fun Intentscreenpreview() {
    Intentscreen(rememberNavController())
}
