package com.example.myntra.presentation.login_signup_screen

import com.example.myntra.data.remote.api.authentication.response.ResendOtpResponse

data class LoginSignupState(
    val loading: Boolean = false,
    val data: ResendOtpResponse? = null,
    val error:String? = null
)