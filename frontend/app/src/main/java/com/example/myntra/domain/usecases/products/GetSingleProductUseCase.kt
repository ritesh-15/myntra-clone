package com.example.myntra.domain.usecases.products

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.api.authentication.body.ActivateBody
import com.example.myntra.data.api.authentication.response.ActivateAccountResponse
import com.example.myntra.data.api.products.response.AllProductsResponse
import com.example.myntra.data.api.products.response.SingleProductResponse
import com.example.myntra.domain.repository.AuthRepository
import com.example.myntra.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSingleProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {

    operator fun invoke(id:String): Flow<Resource<SingleProductResponse>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = repository.getSingleProduct(id)
                emit(Resource.Success(data = response))
            }catch (e: HttpException){
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            }catch (e: IOException){
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }

        }
    }

}