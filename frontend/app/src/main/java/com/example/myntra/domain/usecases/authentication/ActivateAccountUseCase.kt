package com.example.myntra.domain.usecases.authentication

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.authentication.body.ActivateBody
import com.example.myntra.data.remote.api.authentication.response.ActivateAccountResponse
import com.example.myntra.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ActivateAccountUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(body: ActivateBody): Flow<Resource<ActivateAccountResponse>> {
        return flow {
            emit(Resource.Loading())

            try {
                val response = repository.activate(body)
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