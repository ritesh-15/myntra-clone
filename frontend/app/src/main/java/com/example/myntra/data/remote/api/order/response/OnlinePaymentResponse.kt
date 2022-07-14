package com.example.myntra.data.remote.api.order.response

data class OnlinePaymentResponse(
    val message: String,
    val ok: Boolean,
    val payment: OnlinePayment
)