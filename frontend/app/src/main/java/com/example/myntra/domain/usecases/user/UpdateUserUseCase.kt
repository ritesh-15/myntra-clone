package com.example.myntra.domain.usecases.user

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.user.request.UpdateUserRequestBody
import com.example.myntra.domain.model.User
import com.example.myntra.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: UserRepository,
) {

    operator fun invoke(id:String, body:UpdateUserRequestBody): Flow<Resource<User>> {
        return repository.updateUser(id, body)
    }

}