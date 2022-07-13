package com.example.myntra.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myntra.domain.model.Address

@Entity(tableName = "addresses")
data class AddressEntity(
    val address: String,
    val city: String,
    val country: String,
    val createdAt: String,
    @PrimaryKey val id: String,
    val nearestLandmark: String?,
    val phoneNumber: String?,
    val pincode: Int,
    val state: String,
    val updatedAt: String,
    val userId: String,
) {
    fun toAddress(): Address {
        return Address(
            address,
            city,
            country,
            createdAt,
            id,
            nearestLandmark,
            phoneNumber,
            pincode,
            state,
            updatedAt,
            userId
        )
    }
}