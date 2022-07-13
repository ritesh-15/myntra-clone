package com.example.myntra.data.remote.api.order.response

data class SingleOrderResponse(
    val message: String,
    val ok: Boolean,
    val order: Order
)