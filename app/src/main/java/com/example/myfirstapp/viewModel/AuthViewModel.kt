package com.example.myfirstapp.viewModel

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.example.myfirstapp.models.User
import com.example.myfirstapp.navigations.ROUTE_DASHBOARD
import com.example.myfirstapp.navigations.ROUTE_LOGIN
import com.example.myfirstapp.navigations.ROUTE_REGISTER
import com.example.myfirstapp.navigations.ROUTE_USERDASHBOARD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel(var navController: NavHostController, var context: Context) {
    var mAuth = FirebaseAuth.getInstance()

    // register function to create new user
    fun signup(fullnames: String, email: String, password: String, confirmpassword: String) {
        // validation
        if (email.isBlank() || password.isBlank() || confirmpassword.isBlank()) {
            Toast.makeText(context, "email and password cannot be blank", Toast.LENGTH_SHORT).show()
            return
        } else if (password != confirmpassword) {
            Toast.makeText(
                context,
                "password and confirm password do not match",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            // create user
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        val userdata = User(fullnames, password, email, mAuth.currentUser!!.uid,"user")
                        // save user data to realtime database
                        val regRef = FirebaseDatabase.getInstance().getReference()
                            .child("users/" + mAuth.currentUser!!.uid)
                        regRef.setValue(userdata).addOnCompleteListener { dbTask ->
                            if (dbTask.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Registration successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // navigate to login
                                navController.navigate(ROUTE_LOGIN)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Registration failed: ${dbTask.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate(ROUTE_REGISTER)
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Registration failed: ${authTask.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(ROUTE_REGISTER)
                    }
                }
        }
    }

    // login function to authenticate user
    fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userid = mAuth.currentUser?.uid
                    if (userid != null) {
                        // fetch role
                        FirebaseDatabase.getInstance().getReference().child("users")
                            .child(userid).get().addOnSuccessListener { snapshot ->
                                val role = snapshot.child("role").value.toString()
                                // role based navigation
                                if (role == "admin") {
                                    navController.navigate(ROUTE_DASHBOARD)
                                } else {
                                    navController.navigate(ROUTE_USERDASHBOARD)
                                }
                                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    Toast.makeText(context, task.exception?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // signout function to sign out user
    fun signout() {
        mAuth.signOut()
        navController.navigate(ROUTE_LOGIN) { popUpTo(0) }
    }

    // get current user
    fun getcurrentuserName(onResult: (String) -> Unit) {
        val userId = mAuth.currentUser?.uid
        if (userId == null) {
            onResult("user")
            return
        }
        FirebaseDatabase.getInstance().getReference("users")
            .child(userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val fullnames = snapshot.child("fullnames").getValue(String::class.java)
                onResult(fullnames ?: "user")
            }
    }
}
