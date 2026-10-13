package com.example.myfirstapp.products

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myfirstapp.models.product
import com.example.myfirstapp.viewModel.productviewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun updateproductScreen(navController: NavHostController, productId: String) {
    Scaffold(

        // Top Bar
        topBar = {
            TopAppBar(
                title = { Text("UpdateProduct") },
                colors = topAppBarColors(
                    titleContentColor = Color(0xFF8027F5),
                    containerColor = Color(0xFF27F531)
                )
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            Text(
                text = "Update Product",
                fontSize = 28.sp,
            )

            Spacer(modifier = Modifier.height(16.dp))
            var ProductName by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }
            var price by remember { mutableStateOf("") }
            var imageUri by remember { mutableStateOf<Uri?>(null) }
            var imageUrl by remember { mutableStateOf<String?>(null) }
            val imagePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                if (uri != null) imageUri = uri
            }
            val context = LocalContext.current
            val myproductviewmodel = productviewmodel(navController, context)
            //fetch  product data
            LaunchedEffect(productId) {
                myproductviewmodel.databaseReference.child(productId).get()
                    .addOnSuccessListener { snapshot ->
                        val product = snapshot.getValue(product::class.java)
                        product?.let {
                            ProductName = it.name ?: ""
                            description = it.description ?: ""
                            price = it.price ?: ""
                            imageUrl = it.imageUrl
                        }
                    }
            }


            // ProductName
            OutlinedTextField(
                value = ProductName,
                onValueChange = { ProductName = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Price
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            //image preview
            val imageSource: Any? = imageUri ?: imageUrl
            AsyncImage(
                model = imageSource,
                contentDescription = "product",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            //image picker button
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("update Image", color = Color.Blue)
            }

            Button(
                onClick = {
                    myproductviewmodel.updateProduct(
                        productId = productId,
                        name = ProductName,
                        description = description,
                        price = price,
                        imageUri = imageUri
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("UpdateProduct", color = Color.Green)
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun updateproductScreenPreview() {
    updateproductScreen(rememberNavController(), "sample_id")
}
