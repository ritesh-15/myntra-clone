package com.example.myntra.data.remote.api.order.request

data class OrderProductBody(
    val productId: String,
    val quantity: Int,
    val sizeId: String
)