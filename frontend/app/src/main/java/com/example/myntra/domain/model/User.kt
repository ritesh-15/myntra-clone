package com.example.myntra.domain.model

data class User(
    val email: String,
    val id: String,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val isVerified: Boolean,
    val name: String,
    val phoneNumber: String
)