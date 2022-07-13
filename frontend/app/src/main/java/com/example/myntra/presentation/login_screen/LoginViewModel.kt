package com.example.myntra.presentation.login_screen

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myntra.common.Constants
import com.example.myntra.common.utils.Resource
import com.example.myntra.data.remote.api.authentication.body.LoginBody
import com.example.myntra.domain.model.ApiError
import com.example.myntra.domain.model.User
import com.example.myntra.domain.usecases.authentication.LoginUseCase
import com.example.myntra.domain.usecases.user.InsertUserUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.annotation.meta.When
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val pref: SharedPreferences,
    private val insertUserUseCase: InsertUserUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(LoginViewModelState())
    val state: State<LoginViewModelState> = _state

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun onEmailChange(it: String) {
        email.value = it
    }

    fun onPasswordChange(it: String) {
        password.value = it
    }

    private fun storeIntoCache(user: User) {
        viewModelScope.launch {
            insertUserUseCase.invoke(user).collect {
                when(it){
                    is Resource.Success -> {
                        // TODO
                    }
                }
            }
        }
    }

    fun login() {
        val response =
            loginUseCase.invoke(LoginBody(email = email.value, password = password.value))

        viewModelScope.launch {
            response.collect {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = LoginViewModelState(loading = true)
                    }

                    is Resource.Success -> {

                        // save the token
                        pref.edit().apply {
                            putString(Constants.ACCESS_TOKEN, it.data?.tokens?.accessToken)
                            putString(Constants.REFRESH_TOKEN, it.data?.tokens?.refreshToken)
                            apply()
                        }

                        // insert the user in local cache
                        if (it.data != null) {
                            storeIntoCache(it.data.user)
                        }

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