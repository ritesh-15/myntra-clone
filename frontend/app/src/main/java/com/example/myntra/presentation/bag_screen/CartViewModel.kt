package com.example.myntra.presentation.bag_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.cart.GetAllCartProducts
import com.example.myntra.domain.usecases.cart.RemoveFromCartUseCase
import com.example.myntra.domain.usecases.cart.RemoveFromCartUseCase_Factory
import com.example.myntra.domain.usecases.cart.UpdateCartQuantityUseCase
import com.example.myntra.domain.usecases.products.GetAllCategoriesUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCartProducts: GetAllCartProducts,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CartViewModelState())
    val state: StateFlow<CartViewModelState> = _state

    init {
        getAllCartProducts()
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            removeFromCartUseCase.invoke(productId).collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value = CartViewModelState(removeFromCart = productId)
                    }
                }
            }
        }
    }

    fun updateQuantity(id:String, quantity:Int){
        viewModelScope.launch {
            updateCartQuantityUseCase.invoke(id, quantity ).collect{
                when(it){
                    is Resource.Success -> {
                        // TODO
                    }
                }
            }
        }
    }

    fun getAllCartProducts() {

        val response = getAllCartProducts.invoke()

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        if (it.data != null) {
                            _state.value = CartViewModelState(loading = false, products = it.data)
                        } else {
                            _state.value = CartViewModelState(loading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.value = CartViewModelState(loading = false, products = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                CartViewModelState(error = error.message, loading = false)
                        } else {
                            _state.value = CartViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
