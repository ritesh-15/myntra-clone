package com.example.myntra.domain.usecases.user

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.user.request.AddAddressRequestBody
import com.example.myntra.domain.model.Address
import com.example.myntra.domain.model.User
import com.example.myntra.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository,
) {

    operator fun invoke(user:User): Flow<Resource<Any>> {
        return repository.storeUser(user)
    }

}