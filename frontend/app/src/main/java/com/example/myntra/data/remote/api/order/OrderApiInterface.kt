package com.example.myntra.data.remote.api.order

import com.example.myntra.data.remote.api.order.request.CreateOrderRequestBody
import com.example.myntra.data.remote.api.order.request.Payment
import com.example.myntra.data.remote.api.order.request.PaymentRequestBody
import com.example.myntra.data.remote.api.order.response.*
import retrofit2.http.*

interface OrderApiInterface {

    @GET("order/all/user")
    suspend fun getAllOrders(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): AllOrdersResponse


    @GET("order/{id}")
    suspend fun getSingleOrder(
        @Path("id") id: String,
    ): SingleOrderResponse

    @DELETE("order/{id}")
    suspend fun deleteOrder(
        @Path("id") id: String,
    ): DeleteOrderResponse

    @POST("order/create")
    suspend fun createOrder(
        @Body body: CreateOrderRequestBody,
    ): CreateOrderResponse

    @POST("order/payment")
    suspend fun payment(
        @Body body: PaymentRequestBody,
    ): OnlinePaymentResponse
}