package com.example.myntra.data.remote.api.order.response

data class Payment(
    val createdAt: String,
    val discount: Int,
    val id: String,
    val orderId: String,
    val paymentStatus: Boolean,
    val paymentType: String,
    val total: Int,
    val updatedAt: String
)