package com.example.myntra.data.remote.api.order.response

data class Product(
    val id: String,
    val orderId: String,
    val product: ProductX,
    val productId: String,
    val quantity: Int,
    val size: Size,
    val sizeId: String
)