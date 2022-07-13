package com.example.myntra.data.remote.api.user

import com.example.myntra.data.remote.api.user.request.AddAddressRequestBody
import com.example.myntra.data.remote.api.user.request.UpdateUserRequestBody
import com.example.myntra.data.remote.api.user.response.*
import retrofit2.http.*

interface UserApiInterface {

    @GET("user/address/all")
    suspend fun getAllAddresses():AllAddressesResponse

    @DELETE("user/remove-address/{id}")
    suspend fun removeAddress(
        @Path("id") id:String
    ):RemoveAddressResponse

    @GET("user/add-address")
    suspend fun addAddress(
        @Body body:AddAddressRequestBody
    ):AddAddressResponse

    @GET("user/{id}")
    suspend fun getUser(
        @Path("id") id:String
    ):SingleUserResponse

    @PUT("user/{id}")
    suspend fun updateUser(
        @Path("id") id:String,
        @Body body : UpdateUserRequestBody
    ):UpdateUserResponse
}