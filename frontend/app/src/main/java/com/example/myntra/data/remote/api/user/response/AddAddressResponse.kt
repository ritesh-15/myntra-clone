package com.example.myntra.data.remote.api.user.response

import com.example.myntra.domain.model.Address

data class AddAddressResponse(
    val address: Address,
    val message: String,
    val ok: Boolean
)