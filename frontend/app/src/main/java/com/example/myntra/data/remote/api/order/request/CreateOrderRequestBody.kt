package com.example.myntra.data.remote.api.order.request

data class CreateOrderRequestBody(
    val addressId: String,
    val payment: Payment,
    val products: List<OrderProductBody>,
    val razorPayOrderId: String? = null,
)