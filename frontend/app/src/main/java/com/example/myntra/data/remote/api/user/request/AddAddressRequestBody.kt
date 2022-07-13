package com.example.myntra.data.remote.api.user.request

data class AddAddressRequestBody(
    val address: String,
    val city: String,
    val country: String,
    val pinCode: String,
    val state: String
)