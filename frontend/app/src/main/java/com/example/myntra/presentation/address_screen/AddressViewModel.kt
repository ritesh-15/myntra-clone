package com.example.myntra.presentation.address_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.cart.GetAllCartProducts
import com.example.myntra.domain.usecases.cart.RemoveFromCartUseCase
import com.example.myntra.domain.usecases.cart.UpdateCartQuantityUseCase
import com.example.myntra.domain.usecases.user.GetAllAddressesUseCase
import com.example.myntra.domain.usecases.user.RemoveAddressUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAllAddressesUseCase: GetAllAddressesUseCase,
    private val removeAddressUseCase: RemoveAddressUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AddressViewModelState())
    val state: StateFlow<AddressViewModelState> = _state

    init {
        getAllAddresses()
    }


    fun removeAddresses(id: String) {
        viewModelScope.launch {
            removeAddressUseCase.invoke(id).collect {
                when (it) {
                    is Resource.Loading -> {
                        if (it.data != null) {
                            _state.value =
                                AddressViewModelState(loading = false)
                        } else {
                            _state.value = AddressViewModelState(loading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.value = AddressViewModelState(loading = false)
                        getAllAddresses()
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                AddressViewModelState(error = error.message, loading = false)
                        } else {
                            _state.value =
                                AddressViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }

    fun getAllAddresses() {

        val response = getAllAddressesUseCase.invoke()

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        if (it.data != null) {
                            _state.value =
                                AddressViewModelState(loading = false, addresses = it.data)
                        } else {
                            _state.value = AddressViewModelState(loading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.value =
                            AddressViewModelState(loading = false, addresses = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                AddressViewModelState(error = error.message, loading = false)
                        } else {
                            _state.value =
                                AddressViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
