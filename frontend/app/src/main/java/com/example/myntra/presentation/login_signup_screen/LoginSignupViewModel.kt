package com.example.myntra.presentation.login_signup_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.authentication.body.RegisterBody
import com.example.myntra.data.remote.api.authentication.body.ResendOtpBody
import com.example.myntra.data.remote.api.authentication.response.ResendOtpResponse
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.usecases.authentication.RegisterUseCase
import com.example.myntra.domain.usecases.authentication.ResendOtpUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignupViewModel @Inject constructor(
    private val resendOtpUseCase: ResendOtpUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(LoginSignupState())
    val state: State<LoginSignupState> = _state

    val email = mutableStateOf("")

    fun onEmailChange(it:String){
        email.value = it
    }

    fun resendOtpOrRegister(register: Boolean = false) {

        // check if valid email or not
        if (email.value.isEmpty()){
            _state.value = LoginSignupState(error= "Email address is required!")
            return
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()){
            _state.value = LoginSignupState(error= "Email address is not valid!")
            return
        }

        val response = if (register) {
                registerUseCase.invoke(RegisterBody(email = email.value))
            } else {
                resendOtpUseCase.invoke(
                    ResendOtpBody(email = email.value))
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
                                Log.d("register view model","Register function called")
                                resendOtpOrRegister(register = true)
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
