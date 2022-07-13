package com.example.myntra.domain.repository

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.user.request.AddAddressRequestBody
import com.example.myntra.data.remote.api.user.request.UpdateUserRequestBody
import com.example.myntra.data.remote.api.user.response.AddAddressResponse
import com.example.myntra.data.remote.api.user.response.RemoveAddressResponse
import com.example.myntra.domain.model.Address
import com.example.myntra.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllAddresses(): Flow<Resource<List<Address>>>

    fun removeAddress(id:String):Flow<Resource<RemoveAddressResponse>>

    fun addAddress(address:AddAddressRequestBody): Flow<Resource<Address>>

    fun getUser():Flow<Resource<User>>

    fun storeUser(user:User):Flow<Resource<Any>>

    fun logoutUser():Flow<Resource<Any>>

    fun updateUser(id:String,body:UpdateUserRequestBody):Flow<Resource<User>>
}