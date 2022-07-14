package com.example.myntra.presentation.checkout_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.order.request.CreateOrderRequestBody
import com.example.myntra.data.remote.api.order.request.Payment
import com.example.myntra.data.remote.api.order.request.PaymentRequestBody
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.model.User
import com.example.myntra.domain.usecases.cart.EmptyCartUseCase
import com.example.myntra.domain.usecases.cart.GetAllCartProducts
import com.example.myntra.domain.usecases.order.CreateOrderUseCase
import com.example.myntra.domain.usecases.order.MakePaymentUseCase
import com.example.myntra.domain.usecases.user.GetUserUseCase
import com.google.gson.Gson
import com.razorpay.PaymentResultListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getAllCartProducts: GetAllCartProducts,
    private val paymentUseCase: MakePaymentUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val emptyCartUseCase: EmptyCartUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CheckoutViewModelState())
    val state: StateFlow<CheckoutViewModelState> = _state

    val user = mutableStateOf<User?>(null)

    // selected payment method
    val selectedPaymentMethod = mutableStateOf<PaymentMethods>(PaymentMethods.CashOnDelivery)

    fun changePaymentMethod(method: PaymentMethods) {
        selectedPaymentMethod.value = method
    }

    init {
        getAllCartProducts()
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.invoke().collect {
                user.value = it.data
            }
        }
    }

    fun createOrder(
        addressId: String,
        discount: Int,
        paymentType: PaymentMethods,
        total: Int,
        razorPayOrderId: String? = null,
    ) {
        if (state.value.products != null) {
            viewModelScope.launch {
                createOrderUseCase.invoke(
                    CreateOrderRequestBody(
                        addressId = addressId,
                        products = state.value.products ?: emptyList(),
                        payment = Payment(
                            discount = discount,
                            paymentType = paymentType.method,
                            total = total
                        ),
                        razorPayOrderId = razorPayOrderId
                    )
                ).collect {
                    when (it) {
                        is Resource.Loading -> {
                            _state.value = CheckoutViewModelState(loading = true)
                        }

                        is Resource.Success -> {
                            _state.value = CheckoutViewModelState(loading = false,
                                done = true
                            )
                            emptyCart()
                        }

                        is Resource.Error -> {
                            if (it.errorBody != null) {
                                val error = Gson().fromJson(it.errorBody.string(),
                                    ApiError::class.java)
                                _state.value =
                                    CheckoutViewModelState(error = error.message, loading = false)
                            } else {
                                _state.value =
                                    CheckoutViewModelState(loading = false, error = it.message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun emptyCart() {
        viewModelScope.launch {
            emptyCartUseCase.invoke().collect {

            }
        }
    }

    fun makePaymentFromBackend(amount: Int) {
        viewModelScope.launch {
            paymentUseCase.invoke(PaymentRequestBody(amount = amount)).collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value =
                            CheckoutViewModelState(loading = true, products = state.value.products,
                                cartProducts = state.value.cartProducts)
                    }

                    is Resource.Success -> {
                        if (it.data != null) {
                            _state.value = CheckoutViewModelState(payment = it.data,
                                products = state.value.products,
                                cartProducts = state.value.cartProducts)
                        }
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                CheckoutViewModelState(error = error.message,
                                    loading = false,
                                    products = state.value.products,
                                    cartProducts = state.value.cartProducts)
                        } else {
                            _state.value =
                                CheckoutViewModelState(loading = false,
                                    error = it.message,
                                    products = state.value.products,
                                    cartProducts = state.value.cartProducts)
                        }
                    }
                }
            }
        }
    }

    fun getAllCartProducts() {

        val response = getAllCartProducts.invoke()

        viewModelScope.launch {
            response.collect { it ->
                when (it) {
                    is Resource.Loading -> {
                        if (it.data != null) {
                            _state.value = CheckoutViewModelState(loading = false,
                                products = it.data.map { product ->
                                    product.toOrderProduct()
                                }, cartProducts = it.data)
                        } else {
                            _state.value = CheckoutViewModelState(loading = true)
                        }
                    }

                    is Resource.Success -> {
                        if (it.data != null) {
                            _state.value = CheckoutViewModelState(loading = false,
                                products = it.data.map { product ->
                                    product.toOrderProduct()
                                },
                                cartProducts = it.data
                            )
                        }
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                CheckoutViewModelState(error = error.message, loading = false)
                        } else {
                            _state.value =
                                CheckoutViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }

}

