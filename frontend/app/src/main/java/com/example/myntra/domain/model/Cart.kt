package com.example.myntra.domain.model

import com.example.myntra.data.local.entity.CartEntity
import com.example.myntra.data.remote.api.order.request.OrderProductBody
import java.util.*

data class Cart(
    val productId: String,
    val id: String = "",
    val quantity: Int,
    val product: Product,
    val size: Size,
) {
    fun toCartEntity(): CartEntity {
        return CartEntity(
            id = UUID.randomUUID().toString(),
            productId = productId,
            quantity = quantity,
            size = size
        )
    }

    fun toOrderProduct(): OrderProductBody {
        return OrderProductBody(
            productId = productId,
            sizeId = size.id,
            quantity = quantity
        )
    }
}
