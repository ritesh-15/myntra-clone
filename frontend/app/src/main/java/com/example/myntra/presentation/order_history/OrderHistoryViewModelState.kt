package com.example.myntra.presentation.order_history

import com.example.myntra.data.remote.api.order.response.Order

data class OrderHistoryViewModelState(
    val loading:Boolean = false,
    val error:String? = "",
    val orders:List<Order> = emptyList()
)