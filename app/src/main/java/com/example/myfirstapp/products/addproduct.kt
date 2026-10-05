//create AddProductScreen and preview
//add scaffold -top bar
//column layout
//text addproduct
//add 3 outlined textfield for name description and prize
//add product button

package com.example.myfirstapp.screens.addproduct

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.R
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavHostController = rememberNavController()) {
        var ProductName by remember { mutableStateOf("")}
        var description by remember { mutableStateOf("") }
        var price by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract= ActivityResultContracts.GetContent()
    ){
        uri: Uri? ->
        imageUri = uri
    }
        Scaffold(

            // Top Bar
            topBar = {
                TopAppBar(
                    title = { Text("Add Product") },
                    colors = topAppBarColors(
                        titleContentColor = Color(0xFF8027F5),
                        containerColor = Color(0xFF27F531)
                    )
                )
            }

        ) { innerPadding ->

            Column(modifier = Modifier
                    .padding(innerPadding)
                    .padding(20.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            )
            {
                Text(text = "Add Product",
                    fontSize = 28.sp,)

                    Spacer(modifier = Modifier.height(16.dp))


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
                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(150.dp)
                        .clickable { imagePickerLauncher.launch("image/*") }
                ){
                AsyncImage(
                    model = imageUri ?:R.drawable.free,
                    contentDescription = "product",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(140.dp)
                 )
                }
                //image picker
                OutlinedButton(onClick =
                    {imagePickerLauncher.launch("image/*")}) { Text("Select an Image") }

                // Product Button
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) { Text("AddProduct") }
            }
        }
    }

@Composable
fun AsyncImage(x0: Any) {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun AddProductScreenPreview() {
    AddProductScreen()
}

