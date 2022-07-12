package com.example.myntra.domain.repository

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.local.entity.CartEntity
import com.example.myntra.domain.model.Cart
import com.example.myntra.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun getCartProduct(): Flow<Resource<List<Cart>>>

    fun updateQuantity(cartId:String,quantity:Int):Flow<Resource<Any>>

    fun removeFromCart(cartId:String):Flow<Resource<Any>>

    fun addToCart(cart:CartEntity):Flow<Resource<Any>>
}