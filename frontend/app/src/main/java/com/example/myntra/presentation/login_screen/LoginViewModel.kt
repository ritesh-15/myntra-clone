package com.example.myntra.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.data.api.authentication.body.LoginBody
import com.example.myntra.data.api.authentication.response.LoginResponse
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.authentication.LoginUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginViewModelState())
    val state: StateFlow<LoginViewModelState> = _state

    fun login(email: String, password: String) {
        val response = loginUseCase.invoke(LoginBody(email = email, password = password))

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = LoginViewModelState(loading = true)
                    }

                    is Resource.Success -> {
                        _state.value = LoginViewModelState(loading = false, data = it.data)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java).message
                            _state.value = LoginViewModelState(error = error, loading = false)
                        } else {
                            _state.value = LoginViewModelState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }

    }

}