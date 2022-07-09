package com.example.myntra.data.api.authentication.response

data class ResendOtpResponse(
    val hash: String,
    val message: String,
    val ok: Boolean,
    val email:String
)