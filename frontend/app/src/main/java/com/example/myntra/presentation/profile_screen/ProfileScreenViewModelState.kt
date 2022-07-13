package com.example.myntra.presentation.profile_screen

import com.example.myntra.data.remote.api.authentication.response.LogoutResponse
import com.example.myntra.domain.model.User


data class ProfileScreenViewModelState(
    val loading: Boolean = false,
    val data: LogoutResponse? = null,
    val error:String? = null,
    val user: User? = null
)