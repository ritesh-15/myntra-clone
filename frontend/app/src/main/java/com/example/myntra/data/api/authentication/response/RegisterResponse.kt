package com.example.myntra.data.api.authentication.response

data class RegisterResponse(
    val hash: String,
    val message: String,
    val ok: Boolean,
    val email:String
)