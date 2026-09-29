package com.example.myfirstapp.models
import com.example.myfirstapp.R


data class OnboardingItem(
    val title: String,
    val description: String,
    val imageRes: Int
)
val onboardingItem=listOf(
    OnboardingItem(
        title ="welcome to my  app",
        description="discover new products",
        imageRes = R.drawable.onboard5),
OnboardingItem(
     title ="welcome to my  app",
description="discover new products",
imageRes = R.drawable.onboard2),


OnboardingItem(
title ="welcome to my  app",
description="discover new products",
imageRes = R.drawable.onboard1)
)
