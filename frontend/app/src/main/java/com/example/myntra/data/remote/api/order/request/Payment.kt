package com.example.myntra.data.remote.api.order.request

data class Payment(
    val discount: Int,
    val paymentType: String,
    val total: Int
)