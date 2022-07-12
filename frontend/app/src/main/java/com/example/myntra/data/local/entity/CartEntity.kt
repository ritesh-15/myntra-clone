package com.example.myntra.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.myntra.domain.model.Cart


@Entity(tableName = "cart")
data class CartEntity(
    val productId: String,
    @PrimaryKey val id: String,
    val quantity: Int,
)

data class CartWithProduct(
    @Embedded val cart: CartEntity,
    @Relation(
        entity = ProductEntity::class,
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: ProductAndCategoryWithSizeAndImage,
) {
    fun toCart(): Cart {
        return Cart(
            productId = cart.productId,
            id = cart.id,
            quantity = cart.quantity,
            product = product.toProduct()
        )
    }
}