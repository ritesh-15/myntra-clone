package com.example.myntra.data.api.authentication.response

import com.example.myntra.domain.model.Tokens
import com.example.myntra.domain.model.User

data class RefreshResponse(
    val ok: Boolean,
    val tokens: Tokens,
    val user: User
)