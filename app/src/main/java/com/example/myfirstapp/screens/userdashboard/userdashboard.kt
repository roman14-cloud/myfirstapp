package com.example.myfirstapp.screens.userdashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.myfirstapp.R
import com.example.myfirstapp.models.product
import com.example.myfirstapp.viewModel.AuthViewModel
import com.example.myfirstapp.viewModel.productviewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDashboard(navController: NavHostController) {
    var context = LocalContext.current
    var myproductviewmodel = productviewmodel(navController, context)
    var myauthviewmodel = AuthViewModel(navController, context)
    var username by remember { mutableStateOf("loading") }
    var products= remember { mutableStateListOf<product>() }

    //fetch productsfrom firebaseand loggedin user

    LaunchedEffect(Unit) {
        myproductviewmodel.allProducts(products)
        myauthviewmodel.getcurrentuserName { username = it }
    }
    //add scaffold

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("userdashboard") },
                colors = topAppBarColors(
                    containerColor = Color(0xFF27F531),
                    titleContentColor = Color.Black
                )
            )
        },

        bottomBar = {
            NavigationBar(
                containerColor = Color.Cyan
            ) {
                NavigationBarItem(
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

    ) { innerpadding ->
        Column(
            modifier = Modifier
                .padding(innerpadding)
                .padding(15.dp)
     ) {
//            val products = listOf(
//                product("1", "dog", "maltese dog", "5999", R.drawable.dog.toString()),
//                product("2", "loogo", "logos", "999", R.drawable.loogo.toString()),
//                product("3", "nat", "nature", "5469", R.drawable.nat.toString()),
//                product("4", "free", "freee", "8969", R.drawable.free.toString()),
//            )
            Text(
                text = "Welcome ${username}",
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            var searchText by remember { mutableStateOf("") }
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "search icon"
                    )
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Products featured",
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(products) { product ->
                    // ProductCard
                    Card(
                        modifier = Modifier
                            .height(200.dp)
                            .width(150.dp),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)

                        ) {
                            //product image
                            val imageRes = product.imageUrl?.toIntOrNull() ?: R.drawable.dog
                            Image(
                                painter = rememberAsyncImagePainter(product.imageUrl),
                                contentDescription = product.name ?: "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                            )
                            //product name
                            Text(
                                text = product.name ?: "",
                                fontSize = 18.sp,
                                color = Color.Blue,
                                fontWeight = FontWeight.Bold
                            )
                            //product description
                            Text(
                                text = product.description ?: "",
                                fontSize = 14.sp
                            )
                            //product price
                            Text(
                                text = "ksh ${product.price ?: ""}",
                                color = Color.Red
                            )

                        }
                    }


                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun userdashboardpreview() {
    UserDashboard(navController = rememberNavController())

}
