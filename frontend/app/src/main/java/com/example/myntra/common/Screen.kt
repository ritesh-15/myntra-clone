package com.example.myntra.common

sealed class Screen(
    val route: String,
) {
    object Home : Screen("home_screen")
    object Categories : Screen("categories_screen")
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
}