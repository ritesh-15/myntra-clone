package com.example.myntra.presentation.order_history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.order.GetAllOrdersUseCase
import com.example.myntra.presentation.complete_sign_up.ActivateAccountState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(OrderHistoryViewModelState())
    val state: State<OrderHistoryViewModelState> = _state

    init {
        getAllOrders()
    }

    fun getAllOrders() {
        viewModelScope.launch {
            val response = getAllOrdersUseCase.invoke()

            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = OrderHistoryViewModelState(loading = true)
                    }

                    is Resource.Success -> {
                        _state.value = OrderHistoryViewModelState(loading = false,
                            orders = it.data ?: emptyList())
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                OrderHistoryViewModelState(error = error.message, loading = false)
                        } else {
                            _state.value =
                                OrderHistoryViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }

        }

    }


}