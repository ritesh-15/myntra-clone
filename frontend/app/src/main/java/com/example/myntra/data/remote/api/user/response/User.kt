package com.example.myntra.data.remote.api.user.response

import com.example.myntra.domain.model.Address

data class User(
    val address: List<Address>,
    val email: String,
    val id: String,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val isVerified: Boolean,
    val name: String,
    val orders: List<Any>,
    val phoneNumber: String
)