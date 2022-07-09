package com.example.myntra.presentation.login_signup_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.data.api.authentication.body.LoginBody
import com.example.myntra.data.api.authentication.body.RegisterBody
import com.example.myntra.data.api.authentication.body.ResendOtpBody
import com.example.myntra.data.api.authentication.response.ResendOtpResponse
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.authentication.LoginUseCase
import com.example.myntra.domain.usecases.authentication.RegisterUseCase
import com.example.myntra.domain.usecases.authentication.ResendOtpUseCase
import com.example.myntra.presentation.login_screen.LoginViewModelState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignupViewModel @Inject constructor(
    private val resendOtpUseCase: ResendOtpUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(LoginSignupState())
    val state: State<LoginSignupState> = _state

    fun resendOtpOrRegister(email: String, register: Boolean = false) {

        // check if valid email or not
        if (email.isEmpty()){
            _state.value = LoginSignupState(error= "Email address is required!")
            return
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _state.value = LoginSignupState(error= "Email address is not valid!")
            return
        }

        val response = if (register) {
                registerUseCase.invoke(RegisterBody(email = email))
            } else {
                resendOtpUseCase.invoke(
                    ResendOtpBody(email = email))
            }

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = LoginSignupState(loading = true)
                    }

                    is Resource.Success -> {
                        _state.value = LoginSignupState(loading = false, data = it.data as ResendOtpResponse)
                    }

                    is Resource.Error -> {
                        if (it.errorBody != null) {
                            val error = Gson().fromJson(it.errorBody.string(),
                                ApiError::class.java)
                            _state.value = LoginSignupState(error = error.message, loading = false)

                            if (error.status == 400 && !register){
                                resendOtpOrRegister(email = email, register = true)
                            }

                        } else {
                            _state.value = LoginSignupState(loading = false, error = it.message)
                        }
                    }
                }
            }
        }
    }
}
