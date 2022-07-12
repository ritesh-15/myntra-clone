package com.example.myntra.data.remote.api.authentication.body

data class VerifyOtpBody(
    val email:String,
    val hash:String,
    val otp:Int
)
