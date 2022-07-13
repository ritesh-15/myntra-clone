package com.example.myntra.domain.usecases.cart

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.local.entity.CartEntity
import com.example.myntra.domain.model.Cart
import com.example.myntra.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindCartProductUseCase @Inject constructor(
    private val repository: CartRepository,
) {

    operator fun invoke(productId:String): Flow<Resource<Cart>> {
        return repository.findCartProduct(productId)
    }

}