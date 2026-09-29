
package com.example.myfirstapp.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapp.screens.dashboard.Dashboardscreen
import com.example.myfirstapp.screens.login.LoginScreen
import com.example.myfirstapp.screens.onboarding.onboardingScreen
import com.example.myfirstapp.screens.register.RegisterScreen
import com.example.myfirstapp.screens.splashscreen.SplashScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {

        composable(ROUTE_SPLASH) {
            SplashScreen(navController)
        }

        composable(ROUTE_LOGIN) {
            LoginScreen(navController)
        }

        composable(ROUTE_REGISTER) {
            RegisterScreen(navController)
        }
        composable(ROUTE_DASHBOARD) {
            Dashboardscreen(navController)
    }
        composable(ROUTE_ONBOARDING) {
            onboardingScreen(navController)
        }
    }
}



