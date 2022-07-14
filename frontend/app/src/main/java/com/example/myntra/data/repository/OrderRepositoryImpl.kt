package com.example.myntra.data.repository

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.order.OrderApiInterface
import com.example.myntra.data.remote.api.order.request.CreateOrderRequestBody
import com.example.myntra.data.remote.api.order.request.PaymentRequestBody
import com.example.myntra.data.remote.api.order.response.CreateOrderResponse
import com.example.myntra.data.remote.api.order.response.DeleteOrderResponse
import com.example.myntra.data.remote.api.order.response.OnlinePayment
import com.example.myntra.data.remote.api.order.response.Order
import com.example.myntra.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class OrderRepositoryImpl(
    private val api: OrderApiInterface,
) : OrderRepository {
    override fun createOrder(body: CreateOrderRequestBody): Flow<Resource<CreateOrderResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = api.createOrder(body)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }

        }


    override fun getAllOrders(): Flow<Resource<List<Order>>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = api.getAllOrders(page = 1, size = 5)
                emit(Resource.Success(response.orders))
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }

        }

    override fun getSingleOrder(id: String): Flow<Resource<Order>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = api.getSingleOrder(id)
                emit(Resource.Success(response.order))
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }
        }


    override fun payment(body: PaymentRequestBody): Flow<Resource<OnlinePayment>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = api.payment(body)
                emit(Resource.Success(response.payment))
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }
        }

    override fun deleteOrder(id: String): Flow<Resource<DeleteOrderResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = api.deleteOrder(id)
                emit(Resource.Success(response))
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }
        }
}