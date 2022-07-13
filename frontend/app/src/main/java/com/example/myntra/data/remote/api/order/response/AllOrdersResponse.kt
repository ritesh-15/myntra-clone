package com.example.myntra.data.remote.api.order.response

data class AllOrdersResponse(
    val message: String,
    val ok: Boolean,
    val orders: List<Order>,
    val result: Result
)