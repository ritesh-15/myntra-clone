package com.example.myntra.presentation.login_screen

import com.example.myntra.data.api.authentication.response.LoginResponse

data class LoginViewModelState(
    val loading: Boolean = false,
    val data: LoginResponse? = null,
    val error:String? = ""
)
