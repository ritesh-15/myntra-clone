package com.example.myntra.domain.usecases.products

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.products.response.Category
import com.example.myntra.data.remote.api.products.response.SingleCategoryResponse
import com.example.myntra.domain.model.Catagory
import com.example.myntra.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSingleCategoryUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    operator fun invoke(id:String): Flow<Resource<Category>> {
        return repository.getSingleCategory(id)
    }

}