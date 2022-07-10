package com.example.myntra.presentation.verify_otp_screen


import com.example.myntra.data.api.authentication.response.VerifyOtpResponse

data class VerifyOtpState(
    val loading: Boolean = false,
    val data: VerifyOtpResponse? = null,
    val error:String? = null
)