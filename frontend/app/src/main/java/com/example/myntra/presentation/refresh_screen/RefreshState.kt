package com.example.myntra.presentation.refresh_screen

import com.example.myntra.data.api.authentication.response.RefreshResponse


data class RefreshState(
    val loading: Boolean = false,
    val data: RefreshResponse? = null,
    val error:String? = null
)