package com.example.myntra.domain.usecases.order

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.order.request.PaymentRequestBody
import com.example.myntra.data.remote.api.order.response.OnlinePayment
import com.example.myntra.domain.model.Catagory
import com.example.myntra.domain.repository.OrderRepository
import com.example.myntra.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MakePaymentUseCase @Inject constructor(
    private val repository: OrderRepository,
) {

    operator fun invoke(body: PaymentRequestBody):
            Flow<Resource<OnlinePayment>> {
        return repository.payment(body)
    }

}