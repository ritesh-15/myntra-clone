package com.example.myntra.data.remote.api.authentication.response

import com.example.myntra.domain.model.Tokens
import com.example.myntra.domain.model.User


data class VerifyOtpResponse(
    val ok: Boolean,
    val tokens: Tokens,
    val user: User
)