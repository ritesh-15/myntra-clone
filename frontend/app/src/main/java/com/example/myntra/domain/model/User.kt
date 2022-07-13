package com.example.myntra.domain.model

import com.example.myntra.data.local.entity.UserEntity

data class User(
    val email: String,
    val id: String,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val isVerified: Boolean,
    val name: String,
    val phoneNumber: String,
) {
    fun toUserEntity(): UserEntity {
        return UserEntity(email, id, isActive, isAdmin, isVerified, name, phoneNumber)
    }
}