package com.example.myntra.data.remote.api.order.response

data class Extra(
    val createdAt: String,
    val deliveryCost: Int,
    val deliveryDate: String,
    val deliveryType: String,
    val id: String,
    val orderId: String,
    val orderStatus: String,
    val updatedAt: String
)