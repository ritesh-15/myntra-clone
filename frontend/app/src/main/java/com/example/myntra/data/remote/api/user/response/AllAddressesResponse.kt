package com.example.myntra.data.remote.api.user.response

import com.example.myntra.domain.model.Address

data class AllAddressesResponse(
    val addresses: List<Address>,
    val message: String,
    val ok: Boolean,
)