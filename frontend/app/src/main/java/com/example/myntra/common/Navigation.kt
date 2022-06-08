package com.example.myntra.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myntra.presentation.ResetPasswordScreen
import com.example.myntra.presentation.complete_sign_up.CompleteSignUpScreen
import com.example.myntra.presentation.home_screen.HomeScreen
import com.example.myntra.presentation.link_create_account_screen.LinkCreateAccountScreen
import com.example.myntra.presentation.login_screen.LoginScreen
import com.example.myntra.presentation.login_signup_screen.LoginSignUpScreen
import com.example.myntra.presentation.verify_otp_screen.VerifyOtpScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route
        ) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.LoginSignUp.route) {
            LoginSignUpScreen(navController = navController)
        }

        composable(route = Screen.VerifyOtpScreen.route) {
            VerifyOtpScreen(navController = navController)
        }

        composable(route = Screen.LinkCreateAccountScreen.route) {
            LinkCreateAccountScreen(navController = navController)
        }

        composable(route = Screen.CompleteSignUpScreen.route) {
            CompleteSignUpScreen(navController = navController)
        }

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(route = Screen.ResetPasswordScreen.route) {
            ResetPasswordScreen(navController = navController)
        }
    }

}