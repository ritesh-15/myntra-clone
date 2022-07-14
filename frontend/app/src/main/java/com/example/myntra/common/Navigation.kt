package com.example.myntra.common

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myntra.domain.model.User
import com.example.myntra.presentation.ResetPasswordScreen
import com.example.myntra.presentation.address_screen.AddressesScreen
import com.example.myntra.presentation.bag_screen.BagScreen
import com.example.myntra.presentation.categories_screen.CategoriesScreen
import com.example.myntra.presentation.checkout_screen.CheckoutScreen
import com.example.myntra.presentation.choose_address_screen.ChooseAddressBottomBar
import com.example.myntra.presentation.choose_address_screen.ChooseAddressScreen
import com.example.myntra.presentation.complete_sign_up.CompleteSignUpScreen
import com.example.myntra.presentation.explorer_screen.ExplorerScreen
import com.example.myntra.presentation.home_screen.HomeScreen
import com.example.myntra.presentation.link_create_account_screen.LinkCreateAccountScreen
import com.example.myntra.presentation.login_screen.LoginScreen
import com.example.myntra.presentation.login_signup_screen.LoginSignUpScreen
import com.example.myntra.presentation.order_history.OrderHistoryScreen
import com.example.myntra.presentation.order_placed.OrderPlacedScreen
import com.example.myntra.presentation.profile_screen.ProfileScreen
import com.example.myntra.presentation.refresh_screen.RefreshScreen
import com.example.myntra.presentation.single_category_screen.SingleCategoryScreen
import com.example.myntra.presentation.single_product_screen.SingleProductScreen
import com.example.myntra.presentation.update_profile_screen.UpdateProfileScreen
import com.example.myntra.presentation.verify_otp_screen.VerifyOtpScreen
import com.google.gson.Gson

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Home.route,
            arguments = listOf(
                navArgument("userName") {
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            HomeScreen(navController = navController,
                userName = it.arguments?.getString("userName")
            )
        }

        composable(route = Screen.LoginSignUp.route) {
            LoginSignUpScreen(navController = navController)
        }

        composable(route = Screen.Splash.route) {
            RefreshScreen(navController = navController)
        }

        composable(route = Screen.VerifyOtpScreen.route,
            arguments = listOf(
                navArgument(name = Constants.VERIFY_OTP_HASH)
                {
                    type = NavType.StringType
                },
                navArgument(name = Constants.VERIFY_OTP_EMAIL)
                {
                    type = NavType.StringType
                }))
        {
            VerifyOtpScreen(navController = navController,
                it.arguments?.getString(Constants.VERIFY_OTP_EMAIL)!!,
                it.arguments?.getString(Constants.VERIFY_OTP_HASH)!!)
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

        composable(route = Screen.Categories.route) {
            CategoriesScreen(navController = navController)
        }

        composable(route = Screen.SingleCategoryScreen.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            SingleCategoryScreen(navController = navController,
                id = it.arguments?.getString("id") ?: "")
        }

        composable(route = Screen.SingleProductScreen.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            SingleProductScreen(navController = navController,
                id = it.arguments?.getString("id") ?: "")
        }

        composable(route = Screen.CartScreen.route) {
            BagScreen(navController = navController)
        }

        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.UpdateProfileScreen.route) {
            UpdateProfileScreen(navController = navController)
        }

        composable(route = Screen.Explore.route) {
            ExplorerScreen(navController)
        }

        composable(route = Screen.ChooseAddressScreen.route) {
            ChooseAddressScreen(navController)
        }

        composable(route = Screen.OrderPlaced.route) {
            OrderPlacedScreen(navController)
        }

        composable(route = Screen.OrderHistoryScreen.route) {
            OrderHistoryScreen(navController)
        }

        composable(route = Screen.AddressesScreen.route) {
            AddressesScreen(navController)
        }

        composable(route = Screen.CheckoutScreen.route,
            arguments = listOf(
                navArgument("addressId") {
                    type = NavType.StringType
                }
            )
        ) {
            CheckoutScreen(navController = navController,
                addressId = it.arguments?.getString("addressId") ?: "")
        }

    }

}