package com.example.myfirstapp.viewModel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation.NavHostController
import com.example.myfirstapp.models.product
import com.example.myfirstapp.navigations.ROUTE_PRODUCTLIST
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
    val databaseReference by lazy { FirebaseDatabase.getInstance().getReference("products") }

    // functions
    // crud
    // C-create/upload product to firebase realtime database
    fun addproduct(name: String = "", description: String = "", price: String = "", imageUri: Uri?,
    ) {

        val ref = databaseReference.push()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid ?: ""

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val imageUrl = if (imageUri != null) {
                    uploadToCloudinary(context = context, uri = imageUri)
                } else {
                    ""
                }
                // product data to be stored in realtime database
                val productData = mapOf(
                    "id" to ref.key,
                    "name" to name,
                    "description" to description,
                    "price" to price,
                    "userid" to userId,
                    "imageUrl" to imageUrl
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
            throw Exception("Failed to upload image: ${response.message}")}
        val responseBody = response.body?.string()
        val secureUrl = Regex("secure_url\":\"(.*?)\"").find(responseBody ?: "")?.groupValues?.get(1)
        return secureUrl ?: throw Exception("Failed to get image url")
    }
    //r-read product from db
    //fetch products from firebase realtime database
    fun allProducts(products: SnapshotStateList<product>){
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                products.clear()
                for (snap in snapshot.children) {
                    val retrivedProduct = snap.getValue(product::class.java)
                    if (retrivedProduct != null) {
                        if (retrivedProduct.id.isNullOrEmpty()) {
                            retrivedProduct.id = snap.key
                        }
                        products.add(retrivedProduct)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "database error", Toast.LENGTH_SHORT).show()
            }
        })
        }



    //u-update product
    //update existing products in firebase realtime database
    fun updateProduct(productId: String, name: String, description: String, price: String, imageUri: Uri?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newImageUrl = imageUri?.let { uploadToCloudinary(context = context, uri = it) }
                val currentUser = FirebaseAuth.getInstance().currentUser
                val userId = currentUser?.uid ?: ""
                val updates = mutableMapOf<String, Any>(
                    "id" to productId,
                    "name" to name,
                    "description" to description,
                    "price" to price,
                    "userid" to userId
                )
                if (!newImageUrl.isNullOrEmpty()) {
                    updates["imageUrl"] = newImageUrl
                }
                databaseReference.child(productId).updateChildren(updates).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "product updated successfully", Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(ROUTE_PRODUCTLIST)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "product update failed", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    //d-delete product
    //delete product from firebase realtime database
    fun deleteProduct(productId: String) {
        databaseReference.child(productId).removeValue().addOnCompleteListener {
            Toast.makeText(context, "product deleted successfully", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                Toast.makeText(context, "product deletion failed", Toast.LENGTH_SHORT).show()
            }
    }
}

