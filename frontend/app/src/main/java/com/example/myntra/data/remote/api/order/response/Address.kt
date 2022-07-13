package com.example.myntra.data.remote.api.order.response

data class Address(
    val address: String,
    val city: String,
    val country: String,
    val createdAt: String,
    val id: String,
    val nearestLandmark: Any,
    val phoneNumber: Any,
    val pincode: Int,
    val state: String,
    val updatedAt: String,
    val userId: String
)