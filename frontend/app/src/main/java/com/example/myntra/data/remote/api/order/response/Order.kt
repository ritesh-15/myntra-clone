package com.example.myntra.data.remote.api.order.response

import com.example.myntra.domain.model.Address

data class Order(
    val address: Address,
    val addressId: String,
    val createdAt: String,
    val extra: Extra,
    val extraOrderInfoId: String,
    val id: String,
    val payment: List<Payment>,
    val products: List<Product>,
    val updatedAt: String,
    val user: User,
    val userId: String
)