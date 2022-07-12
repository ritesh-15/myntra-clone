package com.example.myntra.domain.usecases.products

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.products.response.AllCategoriesResponse
import com.example.myntra.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: ProductRepository,
) {

    operator fun invoke(): Flow<Resource<AllCategoriesResponse>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = repository.getAllCategories()
                emit(Resource.Success(data = response))
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }

        }
    }

}