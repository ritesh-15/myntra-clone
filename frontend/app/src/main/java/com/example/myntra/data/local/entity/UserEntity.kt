package com.example.myntra.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myntra.domain.model.User

@Entity(tableName = "user")
data class UserEntity(
    val email: String,
    @PrimaryKey val id: String,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val isVerified: Boolean,
    val name: String,
    val phoneNumber: String,
) {
    fun toUser(): User {
        return User(email, id, isActive, isAdmin, isVerified, name, phoneNumber)
    }
}
