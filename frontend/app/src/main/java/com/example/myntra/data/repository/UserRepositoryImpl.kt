package com.example.myntra.data.repository

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.local.dao.UserDao
import com.example.myntra.data.remote.api.user.UserApiInterface
import com.example.myntra.data.remote.api.user.request.AddAddressRequestBody
import com.example.myntra.data.remote.api.user.request.UpdateUserRequestBody
import com.example.myntra.data.remote.api.user.response.RemoveAddressResponse
import com.example.myntra.domain.model.Address
import com.example.myntra.domain.model.User
import com.example.myntra.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val api: UserApiInterface,
    private val dao: UserDao,
) : UserRepository {
    override fun getAllAddresses(): Flow<Resource<List<Address>>> {
        return flow {
            emit(Resource.Loading())
            val addresses = dao.getAllAddresses().map { it.toAddress() }
            emit(Resource.Loading(data = addresses))

            try {
                val response = api.getAllAddresses()
                dao.insertAddresses(response.addresses.map { it.toAddressEntity() })
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }

            val updatedAddresses = dao.getAllAddresses().map { it.toAddress() }
            emit(Resource.Success(data = updatedAddresses))

        }
    }


    override fun removeAddress(id: String): Flow<Resource<RemoveAddressResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                api.removeAddress(id)
                dao.removeAddress(id)
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }

            emit(Resource.Success())
        }
    }

    override fun addAddress(address: AddAddressRequestBody): Flow<Resource<Address>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.addAddress(address)

                dao.insertAddresses(listOf(
                    response.address.toAddressEntity()
                ))

                emit(Resource.Success(data = response.address))
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }

        }
    }

    override fun getUser(): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            val user = dao.getUser()
            emit(Resource.Success(data = user?.toUser()))
        }
    }

    override fun storeUser(user: User): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())
            dao.insertUser(user.toUserEntity())
            emit(Resource.Success())
        }
    }

    override fun logoutUser(): Flow<Resource<Any>> {
        return flow {
            emit(Resource.Loading())
            dao.removeAddressesWhenLogOut()
            dao.logoutUser()
            emit(Resource.Success())
        }
    }

    override fun updateUser(id: String, body: UpdateUserRequestBody): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.updateUser(id, body)
                dao.insertUser(response.user.toUserEntity())
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }

            val updatedUser = dao.getUser()
            emit(Resource.Success(data = updatedUser?.toUser()))
        }
    }

}