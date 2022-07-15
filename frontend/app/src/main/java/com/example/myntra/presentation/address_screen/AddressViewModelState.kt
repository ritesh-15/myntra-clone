package com.example.myntra.presentation.address_screen


import com.example.myntra.domain.model.Address
import com.example.myntra.domain.model.Cart

data class AddressViewModelState(
    val loading: Boolean = false,
    val addresses: List<Address>? = emptyList(),
    val error: String? = null,
    val removeFromCart: String? = null,
)