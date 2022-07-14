package com.example.myntra.presentation.checkout_screen

import com.example.myntra.data.remote.api.order.request.OrderProductBody
import com.example.myntra.data.remote.api.order.response.OnlinePayment
import com.example.myntra.domain.model.Cart
import com.example.myntra.domain.model.User

data class CheckoutViewModelState(
    val loading: Boolean = false,
    val products: List<OrderProductBody>? = emptyList(),
    val cartProducts: List<Cart>? = emptyList(),
    val error: String? = null,
    val removeFromCart: String? = null,
    val payment:OnlinePayment? = null,
    val done:Boolean= false,
    val user:User? = null
)

sealed class PaymentMethods(val method:String){
    object CashOnDelivery:PaymentMethods("CASH_ON_DELIVERY")
    object Cards:PaymentMethods("ONLINE")
    object Wallet:PaymentMethods("ONLINE")
}