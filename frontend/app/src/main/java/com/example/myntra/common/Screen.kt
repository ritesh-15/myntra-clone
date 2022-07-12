package com.example.myntra.common

import com.example.myntra.domain.model.User

sealed class Screen(
    val route: String,
) {
    object Home : Screen("home_screen/{userName}"){
        fun passUser(userName:String?):String{
            return "home_screen/$userName"
        }
    }

    object Categories : Screen("categories_screen")

    object Splash : Screen("splash_screen")

    object Explore : Screen("explore_screen")

    object Profile : Screen("profile_screen")

    object LoginSignUp : Screen("login_signup_screen")

    object VerifyOtpScreen :
        Screen("verify_otp_screen/{${Constants.VERIFY_OTP_EMAIL}}/{${Constants.VERIFY_OTP_HASH}}") {
        fun passEmailAndHash(email:String,hash:String):String {
            return "verify_otp_screen/$email/$hash"
        }
    }

    object LinkCreateAccountScreen : Screen("link_create_account_screen")

    object CompleteSignUpScreen : Screen("complete_signup_screen")

    object LoginScreen : Screen("login_screen")

    object ResetPasswordScreen : Screen("reset_password_screen")

    object SingleCategoryScreen : Screen("single_category_screen/{id}"){
        fun passId(id:String):String {
            return "single_category_screen/$id"
        }
    }

    object SingleProductScreen : Screen("single_product_screen/{id}"){
        fun passId(id:String):String {
            return "single_product_screen/$id"
        }
    }

    object CartScreen:Screen("cart_screen")
}