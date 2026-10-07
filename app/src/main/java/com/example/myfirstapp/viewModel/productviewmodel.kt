package com.example.myfirstapp.viewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.myfirstapp.navigations.ROUTE_PRODUCTLIST
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.InputStream

class productviewmodel(var navController: NavHostController, var context: Context) {
    val cloudinaryUrl = "https://api.cloudinary.com/v1_1/aep2jnow/upload"
    val uploadPreset = "products"
    val databaseReference = FirebaseDatabase.getInstance().getReference("products")

    // functions
    // crud
    // C-create/upload product to firebase realtime database
    fun addproduct(
        productname: String = "",
        productdescription: String = "",
        productprice: String = "",
        imageuri: Uri? = null,
        name: String = productname,
        description: String = productdescription,
        price: String = productprice
    ) {
        val finalName = if (name.isNotEmpty()) name else productname
        val finalDescription = if (description.isNotEmpty()) description else productdescription
        val finalPrice = if (price.isNotEmpty()) price else productprice

        val ref = databaseReference.push()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid ?: ""

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val imageUrl = if (imageuri != null) {
                    uploadToCloudinary(context = context, uri = imageuri)
                } else {
                    ""
                }
                // product data to be stored in realtime database
                val productData = mapOf(
                    "id" to ref.key,
                    "name" to finalName,
                    "description" to finalDescription,
                    "price" to finalPrice,
                    "userid" to userId,
                    "imageurl" to imageUrl
                )
                ref.setValue(productData).addOnCompleteListener { task ->
                    CoroutineScope(Dispatchers.Main).launch {
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show()
                            //navigate to productlist
                            navController.navigate(ROUTE_PRODUCTLIST)
                        } else {
                            Toast.makeText(
                                context,
                                "Error: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Alias for addproduct to handle typo
    fun adddproduct(
        productname: String = "",
        productdescription: String = "",
        productprice: String = "",
        imageuri: Uri? = null,
        name: String = productname,
        description: String = productdescription,
        price: String = productprice
    ) {
        addproduct(productname, productdescription, productprice, imageuri, name, description, price)
    }

    // upload image to cloudinary function
    private fun uploadToCloudinary(context: Context, uri: Uri): String {
        //get selected image
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        //convert images to bytes
        val fileBytes = inputStream?.use { it.readBytes() } ?: throw Exception("Failed to read image data")
//create multipart request body
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                "image.jpg",
                RequestBody.create("image/*".toMediaTypeOrNull(), fileBytes)
            )
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder()
            .url(cloudinaryUrl)
            .post(requestBody)
            .build()

        val response = OkHttpClient().newCall(request).execute()

        if (!response.isSuccessful) {
            throw Exception("Failed to upload image: ${response.message}")
        }

        val responseBody = response.body?.string()
        val secureUrl = Regex("secure_url\":\"(.*?)\"").find(responseBody ?: "")?.groupValues?.get(1)
        return secureUrl ?: throw Exception("Failed to get image url")
    }
    //r-read product from db
    //fetch products from firebase realtime database
    fun allProducts(){

    }
    //u-update product
    //update existing products in firebase realtime database
    fun updateProduct(){

    }
    //d-delete product
    //delete product from firebase realtime database
    fun deleteProduct(){

    }
}
