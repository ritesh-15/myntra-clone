package com.example.myntra.domain.usecases.cart

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.local.entity.CartEntity
import com.example.myntra.data.remote.api.products.response.AllCategoriesResponse
import com.example.myntra.domain.model.Cart
import com.example.myntra.domain.model.Catagory
import com.example.myntra.domain.repository.CartRepository
import com.example.myntra.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository:CartRepository,
) {

    operator fun invoke(cart:CartEntity): Flow<Resource<Any>> {
        return repository.addToCart(cart)
    }

}