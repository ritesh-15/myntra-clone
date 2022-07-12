package com.example.myntra.presentation.complete_sign_up


import com.example.myntra.data.remote.api.authentication.response.ActivateAccountResponse

data class ActivateAccountState(
    val loading: Boolean = false,
    val data: ActivateAccountResponse? = null,
    val error:String? = null
)