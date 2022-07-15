package com.example.myntra.presentation.single_order_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.order.DeleteOrderUseCase
import com.example.myntra.domain.usecases.order.GetSingleOrderUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleOrderViewModel @Inject constructor(
    private val getSingleOrderUseCase: GetSingleOrderUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(SingleOrderViewModelState())
    val state: State<SingleOrderViewModelState> = _state

    fun deleteOrder(id: String) {
        viewModelScope.launch {
            deleteOrderUseCase.invoke(id).collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = SingleOrderViewModelState(loading = true)
                    }

                    is Resource.Success -> {
                        _state.value =
                            SingleOrderViewModelState(loading = false, isDeleted = true)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                SingleOrderViewModelState(error = error.message, loading = false)
                        } else {
                            _state.value =
                                SingleOrderViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }

    fun getOrder(id: String) {
        viewModelScope.launch {
            val response = getSingleOrderUseCase.invoke(id)

            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = SingleOrderViewModelState(loading = true)
                    }

                    is Resource.Success -> {
                        _state.value =
                            SingleOrderViewModelState(loading = false, order = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                SingleOrderViewModelState(error = error.message, loading = false)
                        } else {
                            _state.value =
                                SingleOrderViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }

}