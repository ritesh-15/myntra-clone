package com.example.myntra.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myntra.data.local.entity.CartEntity
import com.example.myntra.data.local.entity.CartWithProduct
import com.example.myntra.domain.model.Cart

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(product: CartEntity)

    @Query("SELECT * FROM cart WHERE id=:productId")
    suspend fun findCartProduct(productId: String): CartWithProduct?

    @Query("SELECT * FROM cart")
    suspend fun getCartProducts(): List<CartWithProduct>

    @Query("DELETE FROM cart WHERE id=:cartId")
    suspend fun removeFromCart(cartId: String)

    @Query("DELETE FROM cart")
    suspend fun emptyCart()

    @Query("UPDATE cart SET quantity=:quantity WHERE id=:cartId")
    suspend fun updateQuantity(cartId: String, quantity: Int): Unit
}