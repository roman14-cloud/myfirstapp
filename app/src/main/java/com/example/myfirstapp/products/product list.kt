package com.example.myfirstapp.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.R
import com.example.myfirstapp.models.product
import com.example.myfirstapp.navigations.ROUTE_ADDPRODUCT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun productlistScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(
        // Top Bar
        topBar = {
            TopAppBar(
                title = {
                    Text("PRODUCT LIST") },
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
        val products = listOf(
            product("1", "dog", "a dog", "6000", R.drawable.dog.toString()),
            product("2", "free", "a logo", "5000", R.drawable.free.toString()),
            product("3", "nat", "a nature picture", "7000", R.drawable.nat.toString()),
            product("4", "onboard1", "onboard1", "3000", R.drawable.onboard1.toString()),
            )


//lazy column
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(6.dp)
        )
        {
            items(products) { item: product ->
                //card
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 10.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(bottom = 15.dp)

                ) {
                    //productimage
                    Image(
                        painter = painterResource(id = item.imageURL!!.toInt()),
                        contentDescription = "product image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()

                    )
                    //column
                    Column(modifier = Modifier.padding(15.dp))
                    {
                        Text(text = item.name!!,
                            color = Color.Blue,
                        fontSize=20.sp)
                        Text(text = item.description!!,
                            color = Color.Gray,
                        fontSize=20.sp)
                        Text(text = "price:Kshs ${item.price!!}",
                            color = Color.Red,
                        fontSize=20.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                  //row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Button(onClick = {},colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red),
                                modifier = Modifier.weight(1f))
                        {
                            Text(text = "Delete", fontWeight = FontWeight.Bold)
                        }


                        Button(onClick = {},colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green),
                            modifier = Modifier.weight(1f))
                        {
                            Text(text = "Update", fontWeight = FontWeight.Bold)
                    }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun productlistScreenPreview() {
    productlistScreen()
}
