package com.example.myntra.domain.repository

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.order.request.CreateOrderRequestBody
import com.example.myntra.data.remote.api.order.request.PaymentRequestBody
import com.example.myntra.data.remote.api.order.response.*
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun createOrder(body: CreateOrderRequestBody): Flow<Resource<CreateOrderResponse>>

    fun getAllOrders(): Flow<Resource<List<Order>>>

    fun getSingleOrder(id: String): Flow<Resource<Order>>

    fun payment(body: PaymentRequestBody): Flow<Resource<OnlinePayment>>

    fun deleteOrder(id: String): Flow<Resource<DeleteOrderResponse>>

}