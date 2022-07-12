package com.example.myntra.domain.usecases.products

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.products.response.SingleProductResponse
import com.example.myntra.domain.model.Product
import com.example.myntra.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSingleProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(id:String): Flow<Resource<Product>> {
        return repository.getSingleProduct(id)
    }
}