package com.example.myntra.domain.model

data class ApiError(
    val message: String,
    val ok: Boolean,
    val status: Int
)