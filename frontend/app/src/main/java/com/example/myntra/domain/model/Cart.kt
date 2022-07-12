package com.example.myntra.domain.model

import com.example.myntra.data.local.entity.CartEntity
import java.util.*

data class Cart(
    val productId: String,
    val id: String = "",
    val quantity: Int,
    val product: Product,
) {
    fun toCartEntity(): CartEntity {
        return CartEntity(
            id = UUID.randomUUID().toString(),
            productId = productId,
            quantity = quantity
        )
    }
}
