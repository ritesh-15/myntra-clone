package com.example.myntra.data.remote.api.user.response

data class SingleUserResponse(
    val message: String,
    val ok: Boolean,
    val user: SingleUserResponseUser
)