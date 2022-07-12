package com.example.myntra.data.remote.api.authentication.response

data class RegisterResponse(
    val hash: String,
    val message: String,
    val ok: Boolean,
    val email:String
)