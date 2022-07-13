package com.example.myntra.data.remote.api.user.response

import com.example.myntra.domain.model.Address

data class SingleUserResponseUser(
    val address: List<Address>,
    val email: String,
    val id: String,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val isVerified: Boolean,
    val name: String,
    val orders: List<SingleUserResponseOrder>,
    val phoneNumber: String,
) {
    fun toUser(): User {
        return User(
            address, email, id, isActive, isAdmin, isVerified, name, orders, phoneNumber
        )
    }
}