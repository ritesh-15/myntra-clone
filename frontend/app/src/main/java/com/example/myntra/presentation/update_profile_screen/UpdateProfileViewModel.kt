package com.example.myntra.presentation.update_profile_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.data.local.entity.CartEntity
import com.example.myntra.data.remote.api.user.request.UpdateUserRequestBody
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.model.Cart
import com.example.myntra.domain.usecases.cart.AddToCartUseCase
import com.example.myntra.domain.usecases.cart.FindCartProductUseCase
import com.example.myntra.domain.usecases.products.GetSingleProductUseCase
import com.example.myntra.domain.usecases.user.GetUserUseCase
import com.example.myntra.domain.usecases.user.UpdateUserUseCase
import com.example.myntra.presentation.profile_screen.ProfileScreenViewModelState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(UpdateProfileViewModelState())
    val state: State<UpdateProfileViewModelState> = _state

    val name = mutableStateOf("")
    val phoneNumber = mutableStateOf("")


    init {
        getUser()
    }

    fun onNameChange(it: String) {
        name.value = it
    }

    fun onPhoneNumberChange(it: String) {
        phoneNumber.value = it
    }


    private fun getUser() {
        viewModelScope.launch {
            getUserUseCase.invoke().collect {
                when (it) {
                    is Resource.Success -> {
                        _state.value = UpdateProfileViewModelState(user = it.data)
                        name.value = it.data?.name ?: ""
                        phoneNumber.value = it.data?.phoneNumber ?: ""

                    }
                    else -> {
                        _state.value = UpdateProfileViewModelState()
                    }
                }
            }
        }
    }

    fun updateUser() {

        if (state.value.user == null) {
            return
        }

        val body = UpdateUserRequestBody(
            name = name.value.ifEmpty {
                state.value.user!!.name
            },
            phoneNumber = phoneNumber.value.ifEmpty {
                state.value.user!!.phoneNumber
            }
        )

        val response = updateUserUseCase.invoke(state.value.user?.id!!, body)

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = UpdateProfileViewModelState(loading = true)
                    }

                    is Resource.Success -> {
                        _state.value =
                            UpdateProfileViewModelState(loading = false, user = it.data, updated = true)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value =
                                UpdateProfileViewModelState(error = error.message, loading = false)
                        } else {
                            _state.value =
                                UpdateProfileViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
