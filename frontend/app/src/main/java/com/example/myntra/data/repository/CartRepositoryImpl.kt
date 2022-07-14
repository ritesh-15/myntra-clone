package com.example.myntra.data.repository

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.local.dao.CartDao
import com.example.myntra.data.local.entity.CartEntity
import com.example.myntra.domain.model.Cart
import com.example.myntra.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CartRepositoryImpl(
    private val dao: CartDao,
) : CartRepository {
    override fun getCartProduct(): Flow<Resource<List<Cart>>> {
        return flow {
            val cart = dao.getCartProducts().map { it.toCart() }
            emit(Resource.Success(data = cart))
        }
    }

    override fun emptyCart(): Flow<Resource<Any>> {
        return flow {
            dao.emptyCart()
            emit(Resource.Success())
        }
    }

    override fun updateQuantity(cartId: String, quantity: Int): Flow<Resource<Any>> {
        return flow {
            dao.updateQuantity(cartId, quantity)
            emit(Resource.Success())
        }
    }

    override fun removeFromCart(cartId: String): Flow<Resource<Any>> {
        return flow {
            dao.removeFromCart(cartId)
            emit(Resource.Success())
        }
    }

    override fun addToCart(cart: CartEntity): Flow<Resource<Any>> {
        return flow {
            dao.addToCart(cart)
            emit(Resource.Success())
        }
    }

    override fun findCartProduct(productId: String): Flow<Resource<Cart>> {
        return flow {
            val product = dao.findCartProduct(productId)
            emit(Resource.Success(data = product?.toCart() ?: null))
        }
    }
}