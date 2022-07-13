package com.example.myntra.domain.model

import com.example.myntra.data.local.entity.AddressEntity

data class Address(
    val address: String,
    val city: String,
    val country: String,
    val createdAt: String,
    val id: String,
    val nearestLandmark: String?,
    val phoneNumber: String?,
    val pincode: Int,
    val state: String,
    val updatedAt: String,
    val userId: String,
) {
    fun toAddressEntity(): AddressEntity {
        return AddressEntity(
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