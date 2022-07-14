package com.example.myntra.domain.usecases.order

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.order.request.CreateOrderRequestBody
import com.example.myntra.data.remote.api.order.request.PaymentRequestBody
import com.example.myntra.data.remote.api.order.response.CreateOrderResponse
import com.example.myntra.data.remote.api.order.response.OnlinePayment
import com.example.myntra.domain.model.Catagory
import com.example.myntra.domain.repository.OrderRepository
import com.example.myntra.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val repository: OrderRepository,
) {

    operator fun invoke(body: CreateOrderRequestBody):
            Flow<Resource<CreateOrderResponse>> {
        return repository.createOrder(body)
    }

}