package com.example.myfirstapp.viewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    // create/upload product to firebase realtime database
    fun addproduct(productname: String, productdescription: String, productprice: String, imageuri: Uri?) {
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
                    "name" to productname,
                    "description" to productdescription,
                    "price" to productprice,
                    "userid" to userId,
                    "imageurl" to imageUrl
                )

                ref.setValue(productData).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to save product: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Alias for addproduct to handle typo
    fun adddproduct(productname: String, productdescription: String, productprice: String, imageuri: Uri?) {
        addproduct(productname, productdescription, productprice, imageuri)
    }

    // upload image to cloudinary function
    private fun uploadToCloudinary(context: Context, uri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.use { it.readBytes() } ?: throw Exception("Failed to read image data")

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
}
