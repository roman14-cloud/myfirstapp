package com.example.myfirstapp.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.navigations.ROUTE_ADDPRODUCT
import com.example.myfirstapp.navigations.ROUTE_PRODUCTLIST
import com.example.myfirstapp.navigations.ROUTE_UPDATEPRODUCT
import com.example.myfirstapp.viewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboardscreen(navController: NavHostController){
    val context = LocalContext.current
    val myAuth = AuthViewModel(navController, context)
    Scaffold(
        topBar = {
        TopAppBar(
            title = { Text("Dashboard") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Cyan,
                titleContentColor = Color.Blue
            ),
            actions = {
                IconButton(onClick = { }){
                    Icon(Icons.Default.Settings, contentDescription = "icon")
                }
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Person, contentDescription = "icon")
                }
                IconButton(onClick = {myAuth.signout()}) {
                    Icon(Icons.Default.ExitToApp, contentDescription = "icon")
                }
            }
        )

},
//bottom bar
        bottomBar = {
            NavigationBar(containerColor = Color.Cyan){
                NavigationBarItem(selected = true,
                    onClick = {  },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home icon")},
                    label = { Text(text = "Home")})

                    NavigationBarItem(selected = false,
                        onClick = {  },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings icon")},
                        label = { Text(text = "Settings")})
                    NavigationBarItem(selected = false,
                        onClick = {  },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Person icon")},
                        label = { Text(text = "Person")})


            }


        },
        //floating action bar
        floatingActionButton = {
            FloatingActionButton(onClick = {}){Icon(Icons.Default.Add, contentDescription = "add icon")}
        }
    ){innerpadding ->
        //COLUMN
        Column(
            modifier = Modifier
            .padding(innerpadding)
                .background(color = Color.Black)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var username  by  remember { mutableStateOf("loading") }
            LaunchedEffect( Unit) {myAuth.getcurrentuserName { username = it } }
            Text("welcome $username!",
                fontSize = 28.sp,
                color = Black)
            Spacer(modifier = Modifier.height(16.dp))
            //row
            Row() {
                DashboarCard(
                    title = "Update product",
                    background = Color.White,
                    onClick = {navController.navigate(ROUTE_UPDATEPRODUCT) }
                )
                DashboarCard(
                    title = "product list",
                    background = Color(0xFF2C3030),
                    onClick = {navController.navigate(ROUTE_PRODUCTLIST) }
                )

            }
            Row() {
                DashboarCard(
                    title = "addproduct",
                    background = Color.Green,
                    onClick = {navController.navigate(ROUTE_ADDPRODUCT)}
                )
                DashboarCard(
                    title = "settings",
                    background = Color.DarkGray,
                    onClick = { }
                )




            }
    }}}
@Preview(showBackground = true)
@Composable
fun Dashboardscreenpreview(){
    Dashboardscreen(rememberNavController())
}
//Dashboard card
@Composable
fun DashboarCard(title:String,
                 background:Color,
                 onClick:() -> Unit){
    Card(
        modifier = Modifier
            .height(150.dp)
            .width(150.dp)
            .padding(8.dp)
            .clickable { onClick() },
        colors  = CardDefaults.cardColors(containerColor = background) ,
                elevation = CardDefaults . cardElevation (defaultElevation = 10.dp),
        shape=RoundedCornerShape(10.dp)
                ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )

        }
    }
}
@Preview(showBackground = true)
@Composable
fun DashboarCardpreview(){
    DashboarCard(
        title = "opera",
        background = Color.Green,
        onClick = {}
    )
}