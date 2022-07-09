package com.example.myntra.data.api.authentication.response


import com.example.myntra.domain.model.User

data class ActivateAccountResponse(
    val ok: Boolean,
    val user: User
)