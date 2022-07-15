package com.example.myntra.presentation.single_order_screen

import com.example.myntra.data.remote.api.order.response.Order

data class SingleOrderViewModelState(
    val loading: Boolean = false,
    val order: Order? = null,
    val error:String? = null,
    val isDeleted:Boolean = false
)